package com.example.android.introvert;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by takeoff on 026 26 Oct 17.
 */

public class IntrovertDbHelper extends SQLiteOpenHelper {

    private String TAG = "INTROWERT_DBHELPER:";

    public static final String DATABASE_NAME = "INTROVERT_db";
    public static final int DATABASE_VERSION = 1;
    public static final String ID_COLUMN = "_id";


    /*SETTINGS table*/
    public static final String SETTINGS_TABLE_NAME = "SETTINGS";
    public static final String SETTINGS_1_COLUMN = "setting_1";
    public static final String SETTINGS_2_COLUMN = "setting_2";
    //table create command
    public static final String SETTINGS_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + SETTINGS_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SETTINGS_1_COLUMN + " INTEGER DEFAULT 0, " +
            SETTINGS_2_COLUMN + " INTEGER DEFAULT 0);";
    //DROP TABLE command
    private static final String SETTINGS_TABLE_DROP_COMMAND = "DROP TABLE "
            + SETTINGS_TABLE_NAME + ";";


    /*NOTES table*/
    public static final String NOTES_TABLE_NAME = "NOTES";
    public static final String NOTES_NAME_COLUMN = "name";
    public static final String NOTES_DATE_COLUMN = "date";
    public static final String NOTES_LOCATION_COLUMN = "location";
    public static final String NOTES_CREATOR_COLUMN = "creator";
    public static final String NOTES_PRIORITY_COLUMN = "priority";
    public static final String NOTES_TAGS_COLUMN = "tags";
    public static final String NOTES_COMMENT_COLUMN = "comment";
    public static final String NOTES_TYPE_COLUMN = "type";
    public static final String NOTES_CONTENT_COLUMN = "content";
    //table create command
    public static final String NOTES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + NOTES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTES_NAME_COLUMN + " TEXT, " +
            // TODO: 004 04 Jan 18 got this date in db: -1065602605 
            NOTES_DATE_COLUMN + " INTEGER DEFAULT " + System.currentTimeMillis() + ", " +
            NOTES_LOCATION_COLUMN + " TEXT, " +
            NOTES_CREATOR_COLUMN + " TEXT, " +
            NOTES_PRIORITY_COLUMN + " INTEGER DEFAULT 3, " +
            NOTES_TAGS_COLUMN + " TEXT, " +
            NOTES_COMMENT_COLUMN + " TEXT, " +
            NOTES_TYPE_COLUMN + " TEXT, " +
            NOTES_CONTENT_COLUMN + " TEXT);";
    //DROP TABLE command
    private static final String NOTES_TABLE_DROP_COMMAND = "DROP TABLE "
            + NOTES_TABLE_NAME + ";";


    /*NOTE_TYPES table*/
    public static final String NOTE_TYPES_TABLE_NAME = "NOTE_TYPES";
    public static final String NOTE_TYPES_TYPE_COLUMN = "type";
    public static final String NOTE_TYPES_NAME_COLUMN = "name";
    public static final String NOTE_TYPES_ORIGIN_COLUMN = "origin";
    public static final String NOTE_TYPES_VALUABLE_COLUMN = "valuable";
    public static final String NOTE_TYPES_PRIORITIZABLE_COLUMN = "prioritizable";
    public static final String NOTE_TYPES_PRIORITY_COLUMN = "priority";
    public static final String NOTE_TYPES_TAGGABLE_COLUMN = "taggable";
    public static final String NOTE_TYPES_EDITABLE_COLUMN = "editable";
    public static final String NOTE_TYPES_COMMENTABLE_COLUMN = "commentable";
    public static final String NOTE_TYPES_AUTO_NAMING_COLUMN = "auto_naming";
    public static final String NOTE_TYPES_LAST_ID_COLUMN = "last_id";
    //table create command
    public static final String NOTE_TYPES_TABLE_CREATE_COMMAND = "CREATE TABLE "
            + NOTE_TYPES_TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE_TYPES_TYPE_COLUMN + " TEXT, " +
            NOTE_TYPES_NAME_COLUMN + " TEXT, " +
            NOTE_TYPES_ORIGIN_COLUMN + " TEXT, " +
            NOTE_TYPES_VALUABLE_COLUMN + " INTEGER, " +
            NOTE_TYPES_PRIORITIZABLE_COLUMN + " INTEGER, " +
            NOTE_TYPES_PRIORITY_COLUMN + " INTEGER, " +
            NOTE_TYPES_TAGGABLE_COLUMN + " INTEGER, " +
            NOTE_TYPES_EDITABLE_COLUMN + " INTEGER, " +
            NOTE_TYPES_COMMENTABLE_COLUMN + " INTEGER, " +
            NOTE_TYPES_AUTO_NAMING_COLUMN + " INTEGER, " +
            NOTE_TYPES_LAST_ID_COLUMN + " INTEGER DEFAULT 0);";
    //DROP TABLE command
    private static final String NOTE_TYPES_TABLE_DROP_COMMAND = "DROP TABLE "
            + NOTE_TYPES_TABLE_NAME + ";";


    private void createDefaultTypes(SQLiteDatabase db) {
        // Text note
        ContentValues textNoteContentValues = new ContentValues();
        textNoteContentValues.put(NOTE_TYPES_TYPE_COLUMN, "TextNote");
        textNoteContentValues.put(NOTE_TYPES_NAME_COLUMN, "Text Note");
        textNoteContentValues.put(NOTE_TYPES_ORIGIN_COLUMN, TypeValues.origin[0]);
        textNoteContentValues.put(NOTE_TYPES_VALUABLE_COLUMN, TypeValues.valueble[0]);
        textNoteContentValues.put(NOTE_TYPES_PRIORITIZABLE_COLUMN, TypeValues.prioritazable[1]);
        textNoteContentValues.put(NOTE_TYPES_PRIORITY_COLUMN, TypeValues.priorities[2]);
        textNoteContentValues.put(NOTE_TYPES_TAGGABLE_COLUMN, TypeValues.taggable[1]);
        textNoteContentValues.put(NOTE_TYPES_EDITABLE_COLUMN, TypeValues.editable[1]);
        textNoteContentValues.put(NOTE_TYPES_COMMENTABLE_COLUMN, TypeValues.commentable[1]);
        textNoteContentValues.put(NOTE_TYPES_AUTO_NAMING_COLUMN, TypeValues.autoNaming[1]);
        if (db.insert(NOTE_TYPES_TABLE_NAME, null,
                textNoteContentValues) == -1) {
            Log.e(TAG, "Error adding default type: text note");
        } else {
            Log.i(TAG, "Text note type added successfully");
        }


        // TODO: 003 03 Jan 18 audio note


        // TODO: 003 03 Jan 18 money value note


        // TODO: 003 03 Jan 18 add default notes via async

    }


    public IntrovertDbHelper(Context context, String name,
                             SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTINGS_TABLE_CREATE_COMMAND);
        Log.i(TAG, "SETTINGS table created successfully");
        db.execSQL(NOTES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "NOTES table created successfully");
        db.execSQL(NOTE_TYPES_TABLE_CREATE_COMMAND);
        Log.i(TAG, "NOTE_TYPES table created successfully");
        createDefaultTypes(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
