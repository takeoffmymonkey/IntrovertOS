package com.example.android.introvert.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.introvert.Notes.Note;

import java.util.ArrayList;

import static com.example.android.introvert.Database.DbHelper.CATEGORIES_CATEGORY_COLUMN;
import static com.example.android.introvert.Database.DbHelper.CATEGORIES_TABLE_NAME;
import static com.example.android.introvert.Database.DbHelper.ID_COLUMN;
import static com.example.android.introvert.Database.DbHelper.INPUT_TYPES_TABLE_NAME;
import static com.example.android.introvert.Database.DbHelper.INPUT_TYPES_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_COMMENT_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_COMMENT_INPUT_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_CONTENT_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_CONTENT_INPUT_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_NAME_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_PRIORITY_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_TABLE_NAME;
import static com.example.android.introvert.Database.DbHelper.NOTES_TAGS_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_TAGS_INPUT_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTES_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_CATEGORY_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_DEFAULT_COMMENT_INPUT_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_DEFAULT_CONTENT_INPUT_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_DEFAULT_NAME_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_DEFAULT_PRIORITY_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_DEFAULT_TAGS_INPUT_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_INTERNAL_TYPE_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_LAST_ID_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_TABLE_NAME;

/**
 * Created by takeoff on 001 01 Jan 18.
 */

public class DbUtils {

    static private String TAG = "INTROWERT_DBUTILS:";


    static boolean addCategory(SQLiteDatabase db, String category) {
        // Check if category is empty
        if (category != null && !category.equals("")) { // category is not empty

            // Check if category already exists
            Cursor cursorCategory = db.query(CATEGORIES_TABLE_NAME,
                    new String[]{ID_COLUMN},
                    CATEGORIES_CATEGORY_COLUMN + "=?", new String[]{category},
                    null, null, null);

            if (cursorCategory.getCount() == 0) { // Category doesn't exist, add it
                cursorCategory.close();

                ContentValues categoryContentValues = new ContentValues();
                categoryContentValues.put(CATEGORIES_CATEGORY_COLUMN, category);

                if (db.insert(CATEGORIES_TABLE_NAME, null, categoryContentValues) == -1) {
                    // insert was unsuccessfull
                    Log.e(TAG, "Error adding category: " + category);
                    return false;
                } else { // insert was successfull
                    Log.i(TAG, "Category added successfully: " + category);
                    return true;
                }
            } else { // Category already exist
                cursorCategory.close();
                Log.e(TAG, "Category already exist: " + category);
                return false;
            }
        } else { // category is empty
            Log.e(TAG, "Category name should not be empty or null: " + category);
            return false;
        }

    }


    static void createDefaultCategories(SQLiteDatabase db) {
        for (String defaultCategory : DBTypeValues.defaultCategories) {
            addCategory(db, defaultCategory);
        }
    }


    static void createInputTypes(SQLiteDatabase db) {
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


    // Create default names for note types - user and system
    static String generateInternalTypeName(boolean user, SQLiteDatabase db) {
        // User: id_ + user
        // System: id_ + system
        String origin;
        if (user) origin = "user";
        else origin = "system";
        // Get next available id
        Cursor cursorNoteTypes = db.query(NOTE_TYPES_TABLE_NAME, new String[]{ID_COLUMN}, null,
                null, null, null, null);
        int id = cursorNoteTypes.getCount() + 1;
        cursorNoteTypes.close();
        return "" + id + "_" + origin;
    }


    // Create default name for note visible to user (will be incremented)
    // Use this when custom name is not provided
    static String generateDefaultName(String category) {
        // Category + " "
        return category + " ";
    }

    // pass null or 0 for default setting
    static void addNoteType(boolean user,
                            SQLiteDatabase db,
                            @Nullable String defaultName,
                            int category,
                            int defaultPriority,
                            int contentInputType,
                            int tagsInputType,
                            int commentInputType) {

        // Generate type name
        String internalTypeName = generateInternalTypeName(user, db);

        // Get default values if custom not provided
        // Check if category is not provided
        if (category == 0) { // Category not provided
            category = 1; // Ideas
        }
        // Check if default priority is not provided
        if (defaultPriority == 0) { // Default priority is not provided
            defaultPriority = 3;
        }
        // Check if content input type is not provided
        if (contentInputType == 0) { // content input type is not provided
            contentInputType = 1; // text
        }
        // Check if tags input type is not provided
        if (tagsInputType == 0) { // tags input type is not provided
            tagsInputType = 1; // text
        }
        // Check if tags input type is not provided
        if (commentInputType == 0) { // comment input type is not provided
            commentInputType = 1; // text
        }
        // Check if default name is provided, if not - generate
        if (defaultName == null || defaultName.equals("")) { // name is not provided
            defaultName = "Temporary default name";
            String categoryName;
            Cursor cursorCategory = db.query(CATEGORIES_TABLE_NAME,
                    new String[]{CATEGORIES_CATEGORY_COLUMN},
                    ID_COLUMN + "=?", new String[]{Integer.toString(category)},
                    null, null, null);
            if (cursorCategory.getCount() == 1) { // Category is found
                cursorCategory.moveToFirst();
                categoryName = cursorCategory.getString(cursorCategory
                        .getColumnIndexOrThrow(CATEGORIES_CATEGORY_COLUMN));
                defaultName = generateDefaultName(categoryName);
            } else { // Category is not found or wrong number of categories is found
                Log.e(TAG, "Category not found or wrong number of categories found. ID: "
                        + category);
            }
            cursorCategory.close();
        }

        // Insert to NOTE_TYPES table
        ContentValues noteTypeContentValues = new ContentValues();
        noteTypeContentValues.put(NOTE_TYPES_INTERNAL_TYPE_COLUMN, internalTypeName);
        noteTypeContentValues.put(NOTE_TYPES_DEFAULT_NAME_COLUMN, defaultName);
        noteTypeContentValues.put(NOTE_TYPES_CATEGORY_COLUMN, category);
        noteTypeContentValues.put(NOTE_TYPES_DEFAULT_PRIORITY_COLUMN, defaultPriority);
        noteTypeContentValues.put(NOTE_TYPES_DEFAULT_CONTENT_INPUT_TYPE_COLUMN, contentInputType);
        noteTypeContentValues.put(NOTE_TYPES_DEFAULT_TAGS_INPUT_TYPE_COLUMN, tagsInputType);
        noteTypeContentValues.put(NOTE_TYPES_DEFAULT_COMMENT_INPUT_TYPE_COLUMN, commentInputType);
        if (db.insert(NOTE_TYPES_TABLE_NAME, null,
                noteTypeContentValues) == -1) { // Error adding
            Log.e(TAG, "Error adding type");
        } else {
            Log.i(TAG, "Type added successfully");
        }

    }


    static void createDefaultNoteTypes(SQLiteDatabase db) {
        // Default text note for Ideas category
        addNoteType(false, db, null, 0, 0, 0,
                0, 0);

        addNoteType(false, db, "Idea (Audio)", 0, 0, 2,
                0, 0);
    }


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

            Log.d(TAG, "|Column: " + i);
            Log.d(TAG, "|Name: " + name);
            Log.d(TAG, "|Type: " + type);
            Log.d(TAG, "|Not Null: " + notNull);
            Log.d(TAG, "|Default: " + dfltValue);

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
        }
        Log.d(TAG, "|=======================================================|");

        cursorRows.close();
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


    private static boolean incrementLastId(SQLiteDatabase db, int noteTypeId) {
        Cursor cursorLastId = db.query(NOTE_TYPES_TABLE_NAME,
                new String[]{NOTE_TYPES_LAST_ID_COLUMN}, ID_COLUMN + "=?",
                new String[]{Integer.toString(noteTypeId)}, null,
                null, null
        );

        // Check if there is such type
        if (cursorLastId.getCount() == 1) { // Row with type is found
            cursorLastId.moveToFirst();
            int lastId = cursorLastId.getInt(cursorLastId.
                    getColumnIndexOrThrow(NOTE_TYPES_LAST_ID_COLUMN));
            cursorLastId.close();
            // Update last id value
            ContentValues lastIdContentValues = new ContentValues();
            lastIdContentValues.put(NOTE_TYPES_LAST_ID_COLUMN, (lastId + 1));
            if (db.update(NOTE_TYPES_TABLE_NAME, lastIdContentValues,
                    ID_COLUMN + "=?",
                    new String[]{Integer.toString(noteTypeId)}) == 1) {
                // Successful update of last id
                Log.i(TAG, "Successfully updated last id of the type: " + noteTypeId);
                return true;
            } else { // Unsuccessful update of last id
                Log.e(TAG, "Error while updating last id of the type: " + noteTypeId);
                return false;
            }
        } else { // Problems while searching for the needed type
            cursorLastId.close();
            Log.e(TAG, "Error: problems when searching for type with id: " + noteTypeId);
            return false;
        }
    }


    public static boolean saveNote(SQLiteDatabase db, Note note) {
        int noteId = note.getNoteId();

        // TODO: 009 09 Jan 18 change getInit input types to getUpdated when ready
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TYPE_COLUMN, note.getType());
        contentValues.put(NOTES_NAME_COLUMN, note.getUpdatedName());
        contentValues.put(NOTES_PRIORITY_COLUMN, note.getUpdatedPriority());
        contentValues.put(NOTES_CONTENT_COLUMN, note.getUpdatedContent());
        contentValues.put(NOTES_CONTENT_INPUT_TYPE_COLUMN, note.getInitContentInputType());
        contentValues.put(NOTES_TAGS_COLUMN, note.getUpdatedTags());
        contentValues.put(NOTES_TAGS_INPUT_TYPE_COLUMN, note.getInitTagsInputType());
        contentValues.put(NOTES_COMMENT_COLUMN, note.getUpdatedComment());
        contentValues.put(NOTES_COMMENT_INPUT_TYPE_COLUMN, note.getInitCommentInputType());

        if (note.exists()) { // Update existing note
            if (db.update(NOTES_TABLE_NAME, contentValues, ID_COLUMN + "=?",
                    new String[]{Integer.toString(noteId)}) == 1) {
                // Update is successful
                Log.i(TAG, "Successfully updated note with id: " + noteId);
                return true;
            } else {
                // Update is not successful
                Log.e(TAG, "Error: unsuccessful update of the note with id: " + noteId);
                return false;
            }
        } else { // Add new note
            if (db.insert(NOTES_TABLE_NAME, null, contentValues) != -1) {
                // Successful insert
                Log.i(TAG, "Successfully added new note: " + noteId);
                // Increment note count for this type
                int noteTypeId = note.getTypeId();
                return incrementLastId(db, noteTypeId);
            } else {
                // Unsuccessful insert
                Log.e(TAG, "Inserting new note was unsuccessful: " + noteId);
                return false;
            }
        }
    }


    public static boolean deleteNote(SQLiteDatabase db, Note note) {
        if (note.exists()) { // Double check that note exists
            int noteId = note.getNoteId();

            // try to delete the note
            if (db.delete(NOTES_TABLE_NAME, ID_COLUMN + "=?",
                    new String[]{Integer.toString(noteId)}) != 0) {
                Log.i(TAG, "Successfully deleted note id: " + noteId);
                return true;
            } else { // some issue while deleting
                Log.e(TAG, "Error: note wasn't deleted properly");
                return false;
            }
        } else { // Note says it doesn't exist
            Log.e(TAG, "Error: note says it doesn't exist: " + note.getNoteId());
            return false;
        }
    }
}
