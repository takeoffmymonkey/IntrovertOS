package com.example.android.introvert;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by takeoff on 026 26 Oct 17.
 */

public class IntrovertDbHelper extends SQLiteOpenHelper {

    String TAG = "INTROWERT_DBHELPER:";

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
            NOTES_DATE_COLUMN + " INTEGER DEFAULT " + System.currentTimeMillis() + ", " +
            NOTES_LOCATION_COLUMN + " TEXT, " +
            NOTES_CREATOR_COLUMN + " TEXT, " +
            NOTES_PRIORITY_COLUMN + " TEXT, " +
            NOTES_TAGS_COLUMN + " TEXT, " +
            NOTES_COMMENT_COLUMN + " TEXT, " +
            NOTES_TYPE_COLUMN + " TEXT, " +
            NOTES_CONTENT_COLUMN + " TEXT);";
    //DROP TABLE command
    private static final String NOTES_TABLE_DROP_COMMAND = "DROP TABLE "
            + NOTES_TABLE_NAME + ";";


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
    }


    public void dumpTable(SQLiteDatabase db, String tableName) {
        Log.i(TAG, "Starting " + tableName + " dump...");
        Log.d(TAG, "|=======================================================|");
        Log.d(TAG, "Table Name: " + tableName);
        Log.d(TAG, "|-------------------------------------------------------|");

        String pragmaCommand = "PRAGMA table_info(" + tableName + ")";
        Cursor cursorColumns = db.rawQuery(pragmaCommand, null);
        int nameIdx = cursorColumns.getColumnIndexOrThrow("name");
        int typeIdx = cursorColumns.getColumnIndexOrThrow("type");
        int notNullIdx = cursorColumns.getColumnIndexOrThrow("notnull");
        int dfltValueIdx = cursorColumns.getColumnIndexOrThrow("dflt_value");


        int columnsNumber = cursorColumns.getCount();
        Log.d(TAG, "Column Number: " + columnsNumber);

        HashMap<String, String> columnsAndTypes = new HashMap<>();
        int i = 1;

        while (cursorColumns.moveToNext()) {

            String name = cursorColumns.getString(nameIdx);
            String type = cursorColumns.getString(typeIdx);
            String notNull = cursorColumns.getString(notNullIdx);
            String dfltValue = cursorColumns.getString(dfltValueIdx);
            columnsAndTypes.put(name, type);

            Log.i(TAG, "Column " + i + ": " + name + ";"
                    + " Type: " + type + ";"
                    + " Not Null: " + notNull + ";"
                    + " Default: " + dfltValue + ";");

            i++;
        }

        cursorColumns.close();


        /*Cursor cursorRows = db.query(tableName, null, null,
                null, null, null, null);

        int rowsNumber = cursorRows.getCount();

        Log.d(TAG, "Number of rows: " + rowsNumber);


        if (rowsNumber > 0) {

            cursorRows.moveToFirst();

            do {
                Log.d(TAG, "|------------------------------------|");
                Log.d(TAG, "|"
                        + "|");

            } while (cursorRows.moveToNext());


        }


        Log.d(TAG, "|------------------------------------|");

        int id = cursorRows.getInt(cursorRows.getColumnIndex(
                IntrovertDbHelper.ID_COLUMN));
        int setting1 = cursorRows.getInt(cursorRows.getColumnIndex(
                IntrovertDbHelper.SETTINGS_1_COLUMN));
        int setting2 = cursorRows.getInt(cursorRows.getColumnIndex(
                IntrovertDbHelper.SETTINGS_2_COLUMN));

        Log.d(TAG, "|------------------------------------|");
        Log.d(TAG, "" + cursorRows.getColumnName(0) + id);
        Log.d(TAG, "SETTING 1: " + setting1);
        Log.d(TAG, "SETTING 2: " + setting2);

        cursorRows.close();
*/

        Log.d(TAG, "|=======================================================|");

    }


    public void viewNotesTable(SQLiteDatabase db) {
        Log.i(TAG, "Starting Notes dump...");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
