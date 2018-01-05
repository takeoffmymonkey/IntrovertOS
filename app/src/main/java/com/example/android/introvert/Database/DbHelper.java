package com.example.android.introvert.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by takeoff on 026 26 Oct 17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private String TAG = "INTROWERT_DBHELPER:";

    public static final String DATABASE_NAME = "INTROVERT_db";
    public static final int DATABASE_VERSION = 1;
    public static final String ID_COLUMN = "_id";


    /*CATEGORIES table*/
    public static final String CATEGORIES_TABLE_NAME = "CATEGORIES";
    private static final String CATEGORIES_CATEGORY_COLUMN = "category";
    //table create command
    private static final String CATEGORIES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + CATEGORIES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORIES_CATEGORY_COLUMN + " TEXT);";
    //DROP TABLE command
    private static final String CATEGORIES_TABLE_DROP_COMMAND = "DROP TABLE "
            + CATEGORIES_TABLE_NAME + ";";


    private void createDefaultCategories(SQLiteDatabase db) {
        // Add default categories to CATEGORIES_TABLE_NAME
        for (String defaultCategory : DBTypeValues.defaultCategories) {
            ContentValues defaultCategoriesContentValues = new ContentValues();
            defaultCategoriesContentValues.put(CATEGORIES_CATEGORY_COLUMN, defaultCategory);
            if (db.insert(CATEGORIES_TABLE_NAME, null,
                    defaultCategoriesContentValues) == -1) {
                Log.e(TAG, "Error adding default category: " + defaultCategory);
            } else {
                Log.i(TAG, "Default category added successfully: " + defaultCategory);
            }
        }
    }


    /*INPUT_TYPES table*/
    public static final String INPUT_TYPES_TABLE_NAME = "INPUT_TYPES";
    private static final String INPUT_TYPES_TYPE_COLUMN = "input_type";
    //table create command
    private static final String INPUT_TYPES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + INPUT_TYPES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            INPUT_TYPES_TYPE_COLUMN + " TEXT);";
    //DROP TABLE command
    private static final String INPUT_TYPES_TABLE_DROP_COMMAND = "DROP TABLE "
            + INPUT_TYPES_TABLE_NAME + ";";


    private void createInputTypes(SQLiteDatabase db) {
        // Add input types to INPUT_TYPES_TABLE_NAME
        for (String inputType : DBTypeValues.inputTypes) {
            ContentValues inputsContentValues = new ContentValues();
            inputsContentValues.put(INPUT_TYPES_TYPE_COLUMN, inputType);
            if (db.insert(INPUT_TYPES_TABLE_NAME, null,
                    inputsContentValues) == -1) {
                Log.e(TAG, "Error adding input type: " + inputType);
            } else {
                Log.i(TAG, "Input type added successfully: " + inputType);
            }
        }
    }


    /*NOTE_TYPES table - settings & data for types*/
    public static final String NOTE_TYPES_TABLE_NAME = "NOTE_TYPES";
    static final String NOTE_TYPES_TYPE_COLUMN = "type"; // internal type name
    static final String NOTE_TYPES_DEFAULT_NAME_COLUMN = "default_name";
    private static final String NOTE_TYPES_CATEGORY_COLUMN = "category";
    private static final String NOTE_TYPES_DEFAULT_PRIORITY_COLUMN = "default_priority";
    private static final String NOTE_TYPES_DEFAULT_CONTENT_INPUT_TYPE_COLUMN = "default_content_input_type";
    private static final String NOTE_TYPES_DEFAULT_COMMENT_INPUT_TYPE_COLUMN = "default_comment_input_type";
    static final String NOTE_TYPES_LAST_ID_COLUMN = "last_id";
    //table create command
    private static final String NOTE_TYPES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + NOTE_TYPES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE_TYPES_TYPE_COLUMN + " TEXT NOT NULL, " +
            NOTE_TYPES_DEFAULT_NAME_COLUMN + " TEXT DEFAULT Note, " +
            NOTE_TYPES_CATEGORY_COLUMN + " INTEGER DEFAULT 1, " + // from CATEGORIES
            NOTE_TYPES_DEFAULT_PRIORITY_COLUMN + " INTEGER DEFAULT 3, " + // from [] DBTypeValues.priorities
            NOTE_TYPES_DEFAULT_CONTENT_INPUT_TYPE_COLUMN + " INTEGER DEFAULT 1, " + // from INPUT_TYPES
            NOTE_TYPES_DEFAULT_COMMENT_INPUT_TYPE_COLUMN + " INTEGER DEFAULT 1, " + // from INPUT_TYPES
            NOTE_TYPES_LAST_ID_COLUMN + " INTEGER DEFAULT 0);";
    //DROP TABLE command
    private static final String NOTE_TYPES_TABLE_DROP_COMMAND = "DROP TABLE "
            + NOTE_TYPES_TABLE_NAME + ";";


    private void createDefaultNoteTypes(SQLiteDatabase db) {

        DbUtils.createNoteType(db, null, 0, 0, 0,
                0);

    }


    /*NOTES table*/
    public static final String NOTES_TABLE_NAME = "NOTES";
    public static final String NOTES_TYPE_COLUMN = "type";
    public static final String NOTES_NAME_COLUMN = "name";
    public static final String NOTES_PRIORITY_COLUMN = "priority";
    public static final String NOTES_TAGS_COLUMN = "tags";
    public static final String NOTES_TAGS_INPUT_TYPE_COLUMN = "tags_input_type";
    public static final String NOTES_COMMENT_COLUMN = "comment";
    public static final String NOTES_COMMENT_INPUT_TYPE_COLUMN = "comment_input_type";
    public static final String NOTES_CONTENT_COLUMN = "content";
    public static final String NOTES_CONTENT_INPUT_TYPE_COLUMN = "content_input_type";

    //table create command
    public static final String NOTES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + NOTES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTES_TYPE_COLUMN + " INTEGER, " +
            NOTES_NAME_COLUMN + " TEXT, " +
            NOTES_PRIORITY_COLUMN + " INTEGER DEFAULT 3, " +
            NOTES_TAGS_COLUMN + " TEXT, " +
            NOTES_TAGS_INPUT_TYPE_COLUMN + " INTEGER, " +
            NOTES_COMMENT_COLUMN + " TEXT, " +
            NOTES_COMMENT_INPUT_TYPE_COLUMN + " INTEGER, " +
            NOTES_CONTENT_INPUT_TYPE_COLUMN + " INTEGER, " +
            NOTES_CONTENT_COLUMN + " TEXT);";
    //DROP TABLE command
    private static final String NOTES_TABLE_DROP_COMMAND = "DROP TABLE "
            + NOTES_TABLE_NAME + ";";


    public DbHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create CATEGORIES table
        db.execSQL(CATEGORIES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "CATEGORIES table created successfully");
        createDefaultCategories(db);

        // Create INPUT_TYPES table
        db.execSQL(INPUT_TYPES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "INPUT_TYPES table created successfully");
        createInputTypes(db);

        // Create NOTE_TYPES table
        db.execSQL(NOTE_TYPES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "NOTE_TYPES table created successfully");
        createDefaultNoteTypes(db);

        // Create NOTES table
        db.execSQL(NOTES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "NOTES table created successfully");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
