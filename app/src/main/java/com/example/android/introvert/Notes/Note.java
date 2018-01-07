package com.example.android.introvert.Notes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.android.introvert.Database.DbHelper.ID_COLUMN;
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
 * Created by takeoff on 029 29 Dec 17.
 */

public class Note {

    String TAG = "INTROWERT_NOTE";

    private boolean exists; // whether the note exists, or it is fresh
    private int noteId; // id of the note from NOTES table or possible new id
    private String type; // name of the type from NOTE_TYPES table
    private int typeId; // id of the type from NOTE_TYPES table
    private String category; // category of the type from NOTE_TYPES table
    private String name; // default or actual
    private int priority; // default or actual
    private String content; // empty or actual
    private int contentInputType; // default or actual
    private String tags; // empty or actual
    private int tagsInputType; // default or actual
    private String comment; // empty or actual
    private int commentInputType; // default or actual


    // id in editMode - id from NOTES; id in addMode - id from NOTE_TYPES
    public Note(SQLiteDatabase db, boolean exists, int id) {
        this.exists = exists;

        if (exists) { // note already exists
            //get note id
            noteId = id;

            // query the whole line from NOTES
            Cursor noteCursor = db.query(NOTES_TABLE_NAME, null, ID_COLUMN + "=?",
                    new String[]{Integer.toString(noteId)}, null, null, null);

            // Assign values
            if (noteCursor.getCount() == 1) { // row is found
                noteCursor.moveToFirst();
                typeId = noteCursor.getInt(noteCursor.getColumnIndexOrThrow(NOTES_TYPE_COLUMN));
                name = noteCursor.getString(noteCursor.getColumnIndexOrThrow(NOTES_NAME_COLUMN));
                priority = noteCursor.getInt(noteCursor.getColumnIndexOrThrow(NOTES_PRIORITY_COLUMN));
                content = noteCursor.getString(noteCursor.getColumnIndexOrThrow(NOTES_CONTENT_COLUMN));
                contentInputType = noteCursor.getInt(noteCursor
                        .getColumnIndexOrThrow(NOTES_CONTENT_INPUT_TYPE_COLUMN));
                tags = noteCursor.getString(noteCursor.getColumnIndexOrThrow(NOTES_TAGS_COLUMN));
                tagsInputType = noteCursor.getInt(noteCursor
                        .getColumnIndexOrThrow(NOTES_TAGS_INPUT_TYPE_COLUMN));
                comment = noteCursor.getString(noteCursor.getColumnIndexOrThrow(NOTES_COMMENT_COLUMN));
                commentInputType = noteCursor.getInt(noteCursor
                        .getColumnIndexOrThrow(NOTES_COMMENT_INPUT_TYPE_COLUMN));
                noteCursor.close();

                // Get type name and category data from NOTE_TYPES table
                Cursor noteTypeCursor = db.query(NOTE_TYPES_TABLE_NAME,
                        new String[]{NOTE_TYPES_CATEGORY_COLUMN, NOTE_TYPES_INTERNAL_TYPE_COLUMN},
                        ID_COLUMN, new String[]{Integer.toString(typeId)},
                        null, null, null);

                if (noteTypeCursor.getCount() == 1) { // row is found
                    noteTypeCursor.moveToFirst();
                    type = noteTypeCursor.getString(noteTypeCursor
                            .getColumnIndexOrThrow(NOTE_TYPES_INTERNAL_TYPE_COLUMN));
                    category = noteTypeCursor.getString(noteTypeCursor
                            .getColumnIndexOrThrow(NOTE_TYPES_CATEGORY_COLUMN));
                    noteTypeCursor.close();
                } else {
                    // row is not found
                    noteTypeCursor.close();
                    Log.e(TAG, "Note type with this id was not found or found more than 1: "
                            + typeId);
                }

            } else { // row is not found
                noteCursor.close();
                Log.e(TAG, "Note with this id was not found or found more than 1: " + noteId);
            }

        } else { // fresh note
            typeId = id;

            // query the whole line from NOTE_TYPES
            Cursor noteTypesCursor = db.query(NOTE_TYPES_TABLE_NAME, null,
                    ID_COLUMN + "=?", new String[]{Integer.toString(typeId)},
                    null, null, null);

            if (noteTypesCursor.getCount() == 1) {
                noteTypesCursor.moveToFirst();
                type = noteTypesCursor.getString(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_INTERNAL_TYPE_COLUMN));
                category = noteTypesCursor.getString(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_CATEGORY_COLUMN));
                priority = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_PRIORITY_COLUMN));
                content = "";
                contentInputType = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_CONTENT_INPUT_TYPE_COLUMN));
                tags = "";
                tagsInputType = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_TAGS_INPUT_TYPE_COLUMN));
                comment = "";
                commentInputType = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_COMMENT_INPUT_TYPE_COLUMN));
                noteId = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_LAST_ID_COLUMN)) + 1;
                name = noteTypesCursor.getString(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_NAME_COLUMN)) + noteId;

            } else {
                // row is not found
                noteTypesCursor.close();
                Log.e(TAG, "Note type with this id was not found or found more than 1: "
                        + typeId);
            }
        }
    }


    public boolean exists() {
        return exists;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getType() {
        return type;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public String getContent() {
        return content;
    }

    public int getContentInputType() {
        return contentInputType;
    }

    public String getTags() {
        return tags;
    }

    public int getTagsInputType() {
        return tagsInputType;
    }

    public String getComment() {
        return comment;
    }

    public int getCommentInputType() {
        return commentInputType;
    }
}