package edu.jsu.mcis.cs408.crosswordmagic.model;

import android.content.Context;
import android.util.Log;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.DAOFactory;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.PuzzleDAO;

public class CrosswordMagicModel extends AbstractModel {

    private final int DEFAULT_PUZZLE_ID = 1;

    private Puzzle puzzle;
    Character[][] letterOld, letterNew = null;
    Integer[][] numberOld, numberNew = null;
    Integer[] dimOld, dimNew = new Integer[]{null,null};

    String aClueOld, aClueNew, dClueOld, dClueNew = null;

    WordDirection guessOld, guessNew = null;

    PuzzleListItem[] pListOld, pListNew = null;

    Context context;

    public CrosswordMagicModel(Context context, int puzzle) {

        this.context = context;

        DAOFactory daoFactory = new DAOFactory(context);
        PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();

        this.puzzle = puzzleDAO.find(puzzle);

    }

    public CrosswordMagicModel(Context context) {

        this.context = context;

        DAOFactory daoFactory = new DAOFactory(context);
        PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();

        this.puzzle = puzzleDAO.find(DEFAULT_PUZZLE_ID);

    }

    public void getTestProperty() {

        String wordCount = (this.puzzle != null ? String.valueOf(puzzle.getSize()) : "none");
        firePropertyChange(CrosswordMagicController.TEST_PROPERTY, null, wordCount);

    }

    public void getLettersProperty() {

        letterOld = letterNew;
        letterNew = puzzle.getLetters();

        //Log.i("MainActivity", "CW MODEL LETTERS P : " + letterNew.length);

        firePropertyChange(CrosswordMagicController.GRID_LETTERS_PROPERTY, letterOld, letterNew);

    }

    public void getNumbersProperty() {

        numberOld = numberNew;
        numberNew = puzzle.getNumbers();

        //Log.i("MainActivity", "CW MODEL NUMBERS P : " + numberNew.length);

        firePropertyChange(CrosswordMagicController.GRID_NUMBERS_PROPERTY, numberOld, numberNew);

    }

    public void getDimensionProperty() {

        dimOld = new Integer[]{dimNew[0], dimNew[1]};
        dimNew = new Integer[]{puzzle.getHeight(), puzzle.getWidth()};

        //Log.i("MainActivity", "CW MODEL DIMS P : " + dimNew[0] + "  " + dimNew[1]);

        firePropertyChange(CrosswordMagicController.GRID_DIMENSION_PROPERTY, dimOld, dimNew);
        //I lost nearly 8 hours of programming time because I had letterOld and letterNew in
        // this property change and didn't realize it

    }

    public void getCluesAcross(){
        aClueOld = aClueNew;
        aClueNew = puzzle.getCluesAcross();

        firePropertyChange(CrosswordMagicController.CLUES_ACROSS, aClueOld, aClueNew);
    }

    public void getCluesDown(){
        dClueOld = dClueNew;
        dClueNew = puzzle.getCluesDown();

        firePropertyChange(CrosswordMagicController.CLUES_DOWN, dClueOld, dClueNew);
    }

    public void setCheckGuess(String[] guess){

        Log.i("MainActivity", "GUESS RECEIVED IN MODEL");

        int box = Integer.parseInt(guess[0]);
        String word = guess[1];

        guessOld = guessNew;
        guessNew = puzzle.checkGuess(box,word);

        Log.i("MainActivity", "CHECK COMPLETE : " + guessNew.toString());

        firePropertyChange(CrosswordMagicController.CHECK_GUESS, guessOld, guessNew);
    }


    public void getPuzzleList(){

        Log.i("MainActivity", "Attempting to retrieve Puzzle List");

        DAOFactory daoFactory = new DAOFactory(context);
            PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();

            pListOld = pListNew;
            pListNew = puzzleDAO.list(daoFactory.getReadableDatabase());


        Log.i("MainActivity", "Puzzle List Ready : " + pListNew.length);

        firePropertyChange(CrosswordMagicController.PUZZLE_LIST_PROPERTY, pListOld, pListNew);

    }
}