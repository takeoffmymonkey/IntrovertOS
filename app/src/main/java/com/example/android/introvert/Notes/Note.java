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

    private final String TAG = "INTROWERT_NOTE";

    private boolean exists; // whether the note exists, or it is fresh
    private int noteId; // id of the note from NOTES table or possible new id
    private String type; // initName of the type from NOTE_TYPES table
    private int typeId; // id of the type from NOTE_TYPES table
    private String category; // category of the type from NOTE_TYPES table

    // Changeable fields
    private String initName; // default or actual
    private int initPriority; // default or actual
    private String initContent; // empty or actual
    private int initContentInputType; // default or actual
    private String initTags; // empty or actual
    private int initTagsInputType; // default or actual
    private String initComment; // empty or actual
    private int initCommentInputType; // default or actual

    // Updated versions of the fields
    private String updatedName; // initially gets same value as initValue
    private int updatedPriority = 0;
    private String updatedContent = "";
    private int updatedContentInputType = 0;
    private String updatedTags = "";
    private int updatedTagsInputType = 0;
    private String updatedComment = "";
    private int updatedCommentInputType = 0;

    private boolean isReadyForSave = false;

    OnReadyForSave onReadyForSave;

    public interface OnReadyForSave{
        void callingBack();
    }

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
                updatedName = initName = noteCursor.getString(noteCursor.getColumnIndexOrThrow(NOTES_NAME_COLUMN));
                initPriority = noteCursor.getInt(noteCursor.getColumnIndexOrThrow(NOTES_PRIORITY_COLUMN));
                initContent = noteCursor.getString(noteCursor.getColumnIndexOrThrow(NOTES_CONTENT_COLUMN));
                initContentInputType = noteCursor.getInt(noteCursor
                        .getColumnIndexOrThrow(NOTES_CONTENT_INPUT_TYPE_COLUMN));
                initTags = noteCursor.getString(noteCursor.getColumnIndexOrThrow(NOTES_TAGS_COLUMN));
                initTagsInputType = noteCursor.getInt(noteCursor
                        .getColumnIndexOrThrow(NOTES_TAGS_INPUT_TYPE_COLUMN));
                initComment = noteCursor.getString(noteCursor.getColumnIndexOrThrow(NOTES_COMMENT_COLUMN));
                initCommentInputType = noteCursor.getInt(noteCursor
                        .getColumnIndexOrThrow(NOTES_COMMENT_INPUT_TYPE_COLUMN));
                noteCursor.close();

                // Get type initName and category data from NOTE_TYPES table
                Cursor noteTypeCursor = db.query(NOTE_TYPES_TABLE_NAME,
                        new String[]{NOTE_TYPES_CATEGORY_COLUMN, NOTE_TYPES_INTERNAL_TYPE_COLUMN},
                        ID_COLUMN + "=?", new String[]{Integer.toString(typeId)},
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
                initPriority = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_PRIORITY_COLUMN));
                initContent = "";
                initContentInputType = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_CONTENT_INPUT_TYPE_COLUMN));
                initTags = "";
                initTagsInputType = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_TAGS_INPUT_TYPE_COLUMN));
                initComment = "";
                initCommentInputType = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_COMMENT_INPUT_TYPE_COLUMN));
                noteId = noteTypesCursor.getInt(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_LAST_ID_COLUMN)) + 1;
                updatedName = initName = noteTypesCursor.getString(noteTypesCursor
                        .getColumnIndexOrThrow(NOTE_TYPES_DEFAULT_NAME_COLUMN)) + noteId;

            } else {
                // row is not found
                noteTypesCursor.close();
                Log.e(TAG, "Note type with this id was not found or found more than 1: "
                        + typeId);
            }
        }


    }

    public void registerCallBack(OnReadyForSave onReadyForSave){
        this.onReadyForSave = onReadyForSave;
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


    // Initial getters
    public String getInitName() {
        return initName;
    }

    public int getInitPriority() {
        return initPriority;
    }

    public String getInitContent() {
        return initContent;
    }

    public int getInitContentInputType() {
        return initContentInputType;
    }

    public String getInitTags() {
        return initTags;
    }

    public int getInitTagsInputType() {
        return initTagsInputType;
    }

    public String getInitComment() {
        return initComment;
    }

    public int getInitCommentInputType() {
        return initCommentInputType;
    }


    // Update setters
    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }

    public void setUpdatedPriority(int updatedPriority) {
        this.updatedPriority = updatedPriority;
    }

    public void setUpdatedContent(String updatedContent) {
        this.updatedContent = updatedContent;
    }

    public void setUpdatedContentInputType(int updatedContentInputType) {
        this.updatedContentInputType = updatedContentInputType;
    }

    public void setUpdatedTags(String updatedTags) {
        this.updatedTags = updatedTags;
    }

    public void setUpdatedTagsInputType(int updatedTagsInputType) {
        this.updatedTagsInputType = updatedTagsInputType;
    }

    public void setUpdatedComment(String updatedComment) {
        this.updatedComment = updatedComment;
    }

    public void setUpdatedCommentInputType(int updatedCommentInputType) {
        this.updatedCommentInputType = updatedCommentInputType;
    }

    public boolean isReadyForSave() {
        return isReadyForSave;
    }


    // Update getters
    public String getUpdatedName() {
        return updatedName;
    }

    public int getUpdatedPriority() {
        return updatedPriority;
    }

    public String getUpdatedContent() {
        return updatedContent;
    }

    public int getUpdatedContentInputType() {
        return updatedContentInputType;
    }

    public String getUpdatedTags() {
        return updatedTags;
    }

    public int getUpdatedTagsInputType() {
        return updatedTagsInputType;
    }

    public String getUpdatedComment() {
        return updatedComment;
    }

    public int getUpdatedCommentInputType() {
        return updatedCommentInputType;
    }

    public void updateReadiness() {
        // TODO: 009 09 Jan 18 make input type change check
        // TODO: 009 09 Jan 18 check for spaces or enters
        // Check if name or content is empty
        if (!updatedName.isEmpty() && !updatedContent.isEmpty()) { // Name and content aren't empty
            // At least 1 field should differ from the init value
            isReadyForSave = !initName.equals(updatedName) || !initContent.equals(updatedContent)
                    || !initTags.equals(updatedTags) || !initComment.equals(updatedContent);

            onReadyForSave.callingBack();

        } else { // Name or content is empty
            isReadyForSave = false;
        }
        Log.i(TAG, "isReadyForSave: " + isReadyForSave);
    }
}