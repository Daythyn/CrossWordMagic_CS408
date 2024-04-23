package edu.jsu.mcis.cs408.crosswordmagic.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.opencsv.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import edu.jsu.mcis.cs408.crosswordmagic.R;
import edu.jsu.mcis.cs408.crosswordmagic.model.Puzzle;
import edu.jsu.mcis.cs408.crosswordmagic.model.Word;
import edu.jsu.mcis.cs408.crosswordmagic.model.WordDirection;

public class PuzzleDAO {

    private final DAOFactory daoFactory;

    PuzzleDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public int create(HashMap<String, String> params) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        int result = create(db, params);
        db.close();
        return result;

    }

    public int create(SQLiteDatabase db, HashMap<String, String> params) {

        /* use this method if there IS already a SQLiteDatabase open */

        String name = daoFactory.getProperty("sql_field_name");
        String description = daoFactory.getProperty("sql_field_description");
        String height = daoFactory.getProperty("sql_field_height");
        String width = daoFactory.getProperty("sql_field_width");

        ContentValues values = new ContentValues();
        values.put(name, params.get(name));
        values.put(description, params.get(description));
        values.put(height, Integer.parseInt(params.get(height)));
        values.put(width, Integer.parseInt(params.get(width)));

        int key = (int)db.insert(daoFactory.getProperty("sql_table_puzzles"), null, values);

        return key;

    }

    public Puzzle find(int id) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        Puzzle result = find(db, id);
        db.close();
        return result;

    }

    public Puzzle find(SQLiteDatabase db, int id) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        Puzzle puzzle = null;

        String query = daoFactory.getProperty("sql_get_puzzle");
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {

            cursor.moveToFirst();

            HashMap<String, String> params = new HashMap<>();

            /* get data for new puzzle */

            /*

            INSERT YOUR CODE HERE

            */
            params.put(daoFactory.getProperty("sql_field_id"), String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_id")))));
            params.put(daoFactory.getProperty("sql_field_name"), String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_name")))));
            params.put(daoFactory.getProperty("sql_field_description"), String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_description")))));
            params.put(daoFactory.getProperty("sql_field_height"), String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_height")))));
            params.put(daoFactory.getProperty("sql_field_width"), String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_width")))));

            if (!params.isEmpty())
                puzzle = new Puzzle(params);

            /* get list of words (if any) to add to puzzle */

            query = daoFactory.getProperty("sql_get_words");
            cursor = db.rawQuery(query, new String[]{ String.valueOf(id) });

            if (cursor.moveToFirst()) {

                cursor.moveToFirst();

                //SET SOME CONSTANTS
                String wordid = daoFactory.getProperty("sql_field_id");
                String puzzleid = daoFactory.getProperty("sql_field_puzzleid");
                String row = daoFactory.getProperty("sql_field_row");
                String column = daoFactory.getProperty("sql_field_column");
                String box = daoFactory.getProperty("sql_field_box");
                String direction = daoFactory.getProperty("sql_field_direction");
                String word = daoFactory.getProperty("sql_field_word");
                String clue = daoFactory.getProperty("sql_field_clue");

                Word tempWord = null;

                do {

                    params = new HashMap<>();

                    /* get data for the next word in the puzzle */

                    /*

                    INSERT YOUR CODE HERE

                    */
                    params.put(wordid, String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(wordid))));
                    params.put(puzzleid, String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(puzzleid))));
                    params.put(row, String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(row))));
                    params.put(column, String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(column))));
                    params.put(box, String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(box))));
                    params.put(direction, String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(direction))));
                    params.put(word, String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(word))));
                    params.put(clue, String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(clue))));

                    //Log.i("MainActivity", "PUZZLE DAO WORD PARAMS:   " + params.toString());


                    if (!params.isEmpty())
                        tempWord = new Word(params);
                        //Log.i("MainActivity", "PUZZLE DAO WORD tempWord Test:   " + tempWord.toString());
                        puzzle.addWordToPuzzle(tempWord);

                }
                while ( cursor.moveToNext() );

                cursor.close();

            }

            /* get list of already-guessed words (if any) from "guesses" table */

            query = daoFactory.getProperty("sql_get_guesses");
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {

                cursor.moveToFirst();

                do {

                    Integer box = cursor.getInt(4);
                    WordDirection direction = WordDirection.values()[cursor.getInt(5)];

                    if (puzzle != null)
                        puzzle.addWordToGuessed(box + direction.toString());

                }
                while ( cursor.moveToNext() );

                cursor.close();

            }

        }

        return puzzle;

    }

}