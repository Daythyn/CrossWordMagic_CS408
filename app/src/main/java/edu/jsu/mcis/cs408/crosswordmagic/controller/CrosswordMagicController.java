package edu.jsu.mcis.cs408.crosswordmagic.controller;

import android.util.Log;

public class CrosswordMagicController extends AbstractController {

    public static final String TEST_PROPERTY = "TestProperty";
    public static final String GRID_DIMENSION_PROPERTY = "DimensionProperty";
    public static final String GRID_LETTERS_PROPERTY = "LettersProperty";
    public static final String GRID_NUMBERS_PROPERTY = "NumbersProperty";
    public static final String CLUES_ACROSS = "CluesAcross";
    public static final String CLUES_DOWN = "CluesDown";
    public static final String CHECK_GUESS = "CheckGuess";
    public static final String ADD_GUESS = "AddGuess";

    public void getTestProperty(String value) {
        getModelProperty(TEST_PROPERTY);
    }

    public void getGridDimensions() {

        //Log.i("MainActivity", "GETTING GRID DIM PROP");

        getModelProperty(GRID_DIMENSION_PROPERTY);
    }

    public void getGridLetters() {
        getModelProperty(GRID_LETTERS_PROPERTY);
    }

    public void getGridNumbers() {
        getModelProperty(GRID_NUMBERS_PROPERTY);
    }

    public void getCluesAcross() {
        getModelProperty(CLUES_ACROSS);
    }

    public void getCluesDown() { getModelProperty(CLUES_DOWN);}

    public void getCheckGuess(String[] guess) {

        Log.i("MainActivity", "SHIPPING GUESS TO MODEL");

        setModelProperty(CHECK_GUESS,guess);

    }


}



        //controller.getGridDimensions();
        //controller.getGridLetters();
        //controller.getGridNumbers();