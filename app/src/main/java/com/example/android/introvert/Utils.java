package com.example.android.introvert;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import static com.example.android.introvert.IntrovertDbHelper.SETTINGS_TABLE_NAME;

/**
 * Created by takeoff on 001 01 Jan 18.
 */

public class Utils {

    static private String TAG = "INTROWERT_UTILS:";

    public static void dumpTableExternal(SQLiteDatabase db, String tableName) {
        new DumpTable(db, tableName).execute();
    }

    private static void dumpTableInternal(SQLiteDatabase db, String tableName) {

        //Table Heading
        Log.i(TAG, "Starting " + tableName + " dump...");
        Log.d(TAG, "|=======================================================|");
        Log.d(TAG, "|TABLE NAME: " + tableName);
        Log.d(TAG, "|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");


        //Columns info
        String pragmaCommand = "PRAGMA table_info(" + tableName + ")";
        Cursor cursorColumns = db.rawQuery(pragmaCommand, null);
        int nameIdx = cursorColumns.getColumnIndexOrThrow("name");
        int typeIdx = cursorColumns.getColumnIndexOrThrow("type");
        int notNullIdx = cursorColumns.getColumnIndexOrThrow("notnull");
        int dfltValueIdx = cursorColumns.getColumnIndexOrThrow("dflt_value");

        int columnsNumber = cursorColumns.getCount();
        Log.d(TAG, "|NUMBER OF COLUMNS: " + columnsNumber);

        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> columnTypes = new ArrayList<>();
        int i = 1;

        while (cursorColumns.moveToNext()) {
            Log.i(TAG, "|-------------------------------------------------------|");
            String name = cursorColumns.getString(nameIdx);
            String type = cursorColumns.getString(typeIdx);
            String notNull = cursorColumns.getString(notNullIdx);
            String dfltValue = cursorColumns.getString(dfltValueIdx);
            columnNames.add(name);
            columnTypes.add(type);

            Log.i(TAG, "|Column: " + i);
            Log.i(TAG, "|Name: " + name);
            Log.i(TAG, "|Type: " + type);
            Log.i(TAG, "|Not Null: " + notNull);
            Log.i(TAG, "|Default: " + dfltValue);

            i++;
        }

        cursorColumns.close();


        //Rows info
        Cursor cursorRows = db.query(tableName, null, null,
                null, null, null, null);

        int rowsNumber = cursorRows.getCount();
        Log.d(TAG, "|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
        Log.d(TAG, "|NUMBER OF ROWS: " + rowsNumber);

        if (rowsNumber > 0) {

            //Going through each row
            while (cursorRows.moveToNext()) {
                Log.d(TAG, "|-------------------------------------------------------|");

                //Going through each column
                for (int b = 0; b < columnsNumber; b++) {
                    String columnName = columnNames.get(b);
                    String columnValue = null;
                    if (columnTypes.get(b).equals("INTEGER")) {
                        columnValue = Integer.toString(cursorRows.getInt(
                                cursorRows.getColumnIndex(columnName)));
                    } else if (columnTypes.get(b).equals("TEXT")) {
                        columnValue = cursorRows.getString(
                                cursorRows.getColumnIndex(columnName));
                    } else {
                        Log.e(TAG, "UNKNOWN TYPE OF ROW ENTRY!");
                    }

                    Log.i(TAG, "|" + columnName + ": " + columnValue);

                }
            }
            Log.d(TAG, "|=======================================================|");

            cursorRows.close();
        }
    }


    public static class DumpTable extends AsyncTask<String, Void, Boolean> {
        SQLiteDatabase db;
        String tableName;

        DumpTable(SQLiteDatabase db, String tableName) {
            this.db = db;
            this.tableName = tableName;
        }

        protected Boolean doInBackground(String... params) {
            dumpTableInternal(db, tableName);
            return true;
        }

        protected void onPostExecute() {
        }
    }


}
