package com.example.android.introvert.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.introvert.Activities.MainActivity;

import static com.example.android.introvert.Utils.DbUtils.createDefaultCategories;
import static com.example.android.introvert.Utils.DbUtils.createDefaultNoteTypes;
import static com.example.android.introvert.Utils.DbUtils.createInputTypes;

/**
 * Created by takeoff on 026 26 Oct 17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private final String TAG = "INTROWERT_DBHELPER";

    public static final String DATABASE_NAME = "INTROVERT_db";
    public static final int DATABASE_VERSION = 1;
    public static final String ID_COLUMN = "_id";


    /*CATEGORIES table*/
    public static final String CATEGORIES_TABLE_NAME = "CATEGORIES";
    public static final String CATEGORIES_CATEGORY_COLUMN = "category";
    //table create command
    static final String CATEGORIES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + CATEGORIES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORIES_CATEGORY_COLUMN + " TEXT NOT NULL);";
    //DROP TABLE command
    static final String CATEGORIES_TABLE_DROP_COMMAND = "DROP TABLE "
            + CATEGORIES_TABLE_NAME + ";";


    /*INPUT_TYPES table*/
    public static final String INPUT_TYPES_TABLE_NAME = "INPUT_TYPES";
    public static final String INPUT_TYPES_TYPE_COLUMN = "input_type";
    public static final String INPUT_TYPES_CONTENT_LOCATION = "content_location";
    //table create command
    static final String INPUT_TYPES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + INPUT_TYPES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            INPUT_TYPES_CONTENT_LOCATION + " TEXT NOT NULL, " +
            INPUT_TYPES_TYPE_COLUMN + " TEXT NOT NULL);";
    //DROP TABLE command
    static final String INPUT_TYPES_TABLE_DROP_COMMAND = "DROP TABLE "
            + INPUT_TYPES_TABLE_NAME + ";";


    /*NOTE_TYPES table - settings & data for types*/
    public static final String NOTE_TYPES_TABLE_NAME = "NOTE_TYPES";
    public static final String NOTE_TYPES_INTERNAL_TYPE_COLUMN = "internal_type";
    public static final String NOTE_TYPES_DEFAULT_NAME_COLUMN = "default_name"; // name for notes
    public static final String NOTE_TYPES_CATEGORY_COLUMN = "category";
    public static final String NOTE_TYPES_DEFAULT_PRIORITY_COLUMN = "default_priority";
    public static final String NOTE_TYPES_DEFAULT_CONTENT_INPUT_TYPE_COLUMN = "default_content_input_type";
    public static final String NOTE_TYPES_DEFAULT_TAGS_INPUT_TYPE_COLUMN = "default_tags_input_type";
    public static final String NOTE_TYPES_DEFAULT_COMMENT_INPUT_TYPE_COLUMN = "default_comment_input_type";
    public static final String NOTE_TYPES_LAST_ID_COLUMN = "last_id";
    //table create command
    static final String NOTE_TYPES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + NOTE_TYPES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE_TYPES_INTERNAL_TYPE_COLUMN + " TEXT NOT NULL, " +
            NOTE_TYPES_DEFAULT_NAME_COLUMN + " TEXT NOT NULL, " +
            NOTE_TYPES_CATEGORY_COLUMN + " INTEGER NOT NULL, " +
            NOTE_TYPES_DEFAULT_PRIORITY_COLUMN + " INTEGER NOT NULL, " +
            NOTE_TYPES_DEFAULT_CONTENT_INPUT_TYPE_COLUMN + " INTEGER NOT NULL, " +
            NOTE_TYPES_DEFAULT_TAGS_INPUT_TYPE_COLUMN + " INTEGER NOT NULL, " +
            NOTE_TYPES_DEFAULT_COMMENT_INPUT_TYPE_COLUMN + " INTEGER NOT NULL, " +
            NOTE_TYPES_LAST_ID_COLUMN + " INTEGER DEFAULT 0);";
    //DROP TABLE command
    static final String NOTE_TYPES_TABLE_DROP_COMMAND = "DROP TABLE "
            + NOTE_TYPES_TABLE_NAME + ";";


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
    static final String NOTES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + NOTES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTES_TYPE_COLUMN + " INTEGER NOT NULL, " +
            NOTES_NAME_COLUMN + " TEXT NOT NULL, " +
            NOTES_PRIORITY_COLUMN + " INTEGER, " +
            NOTES_TAGS_COLUMN + " TEXT, " +
            NOTES_TAGS_INPUT_TYPE_COLUMN + " INTEGER, " +
            NOTES_COMMENT_COLUMN + " TEXT, " +
            NOTES_COMMENT_INPUT_TYPE_COLUMN + " INTEGER, " +
            NOTES_CONTENT_INPUT_TYPE_COLUMN + " INTEGER NOT NULL, " +
            NOTES_CONTENT_COLUMN + " TEXT NOT NULL);";
    //DROP TABLE command
    static final String NOTES_TABLE_DROP_COMMAND = "DROP TABLE "
            + NOTES_TABLE_NAME + ";";


    public DbHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // In case methods below will have no db to work with
        MainActivity.db = db;

        // Create CATEGORIES table, add default categories
        db.execSQL(CATEGORIES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "CATEGORIES table created successfully");
        createDefaultCategories();

        // Create INPUT_TYPES table, add standard input types
        db.execSQL(INPUT_TYPES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "INPUT_TYPES table created successfully");
        createInputTypes();

        // Create NOTE_TYPES table, add default note types
        db.execSQL(NOTE_TYPES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "NOTE_TYPES table created successfully");
        createDefaultNoteTypes();

        // Create NOTES table
        db.execSQL(NOTES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "NOTES table created successfully");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
