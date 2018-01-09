package com.example.android.introvert.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.introvert.Database.DbUtils;
import com.example.android.introvert.Editors.TextEditor;
import com.example.android.introvert.Fragments.TimelineFragment;
import com.example.android.introvert.Notes.Note;
import com.example.android.introvert.R;

/**
 * Created by takeoff on 029 29 Dec 17.
 */

public class NoteActivity extends AppCompatActivity {

    String TAG = "INTROWERT_NOTE_ACTIVITY:";

    Button saveButton;
    Button deleteButton;
    Button cancelButton;

    SQLiteDatabase db = MainActivity.db;
    boolean exists = false;
    int noteId = 0; // if exists - this is Id of the note; for don't exists - this is Id for type
    Note note;
    boolean isDirty = false; // there are legitimate changes to save

    private LinearLayout contentEditorContainer;
    private LinearLayout tagsEditorContainer;
    private LinearLayout commentEditorContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        // Get passed noteId and mode from extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteId = extras.getInt(TimelineFragment.ID, 0);
            exists = extras.getBoolean(TimelineFragment.EXISTS, false);
        }


        // Create Note object
        note = new Note(db, exists, noteId);


        // Create name field, set content and change listener
        TextView noteNameTextView = (TextView) findViewById(R.id.a_note_note_name_tv);
        EditText noteNameEditText = (EditText) findViewById(R.id.a_note_note_name_et);
        noteNameEditText.setText(note.getInitName());
        TextWatcher nameTextWatcher = makeTextWatcher("Name", noteNameEditText, null);
        noteNameEditText.addTextChangedListener(nameTextWatcher);


        // Add content text view and appropriate editor
        TextView noteContentTextView = (TextView) findViewById(R.id.a_note_note_content_tv);
        if (note.getInitContentInputType() == 1) { // We have a text content input
            contentEditorContainer = (LinearLayout) findViewById(R.id.a_note_content_editor_container);
            TextEditor contentEditor = new TextEditor(getApplicationContext(), 1);
            contentEditor.setEditTextHint("Enter you note here");
            contentEditorContainer.addView(contentEditor);
            if (note.exists()) { // We have an existing note, need to set it's content
                contentEditor.setEditTextText(note.getInitContent());
            }
            // Add listener
            TextWatcher contentTextWatcher
                    = makeTextWatcher("Content", null, contentEditor);
            contentEditor.addListener(contentTextWatcher);
        }


        // Add tags text view and appropriate editor
        TextView noteTagsTextView = (TextView) findViewById(R.id.a_note_note_tags_tv);
        if (note.getInitTagsInputType() == 1) { // We have a text tags input
            tagsEditorContainer = (LinearLayout) findViewById(R.id.a_note_tags_editor_container);
            TextEditor tagsEditor = new TextEditor(getApplicationContext(), 2);
            tagsEditor.setEditTextHint("Enter you tags here");
            tagsEditorContainer.addView(tagsEditor);
            if (note.exists()) { // We have an existing note, need to set it's tags
                tagsEditor.setEditTextText(note.getInitTags());
            }
            // Add listener
            TextWatcher tagsTextWatcher
                    = makeTextWatcher("Tags", null, tagsEditor);
            tagsEditor.addListener(tagsTextWatcher);
        }


        // Add comment text view and appropriate editor
        TextView noteCommentTextView = (TextView) findViewById(R.id.a_note_note_comment_tv);
        if (note.getInitCommentInputType() == 1) { // We have a text comment input
            commentEditorContainer = (LinearLayout) findViewById(R.id.a_note_comment_editor_container);
            TextEditor commentEditor = new TextEditor(getApplicationContext(), 3);
            commentEditor.setEditTextHint("Enter you comment here");
            commentEditorContainer.addView(commentEditor);
            if (note.exists()) { // We have an existing note, need to set it's tags
                commentEditor.setEditTextText(note.getInitComment());
            }
            // Add listener
            TextWatcher commentTextWatcher
                    = makeTextWatcher("Comment", null, commentEditor);
            commentEditor.addListener(commentTextWatcher);
        }


        // Add settings text view and set onclick listener
        TextView settingsTextView = (TextView) findViewById(R.id.a_note_settings_tv);
        settingsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoteActivity.this, "Settings pressed", Toast.LENGTH_SHORT).show();
            }
        });


        // Create buttons
        saveButton = (Button) findViewById(R.id.a_note_save_b);
        cancelButton = (Button) findViewById(R.id.a_note_cancel_b);
        deleteButton = (Button) findViewById(R.id.a_note_delete_b);


        // Add click listeners to buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 002 02 Jan 18 remove current activity from stack
                Log.i(TAG, "save pressed");
                if (DbUtils.saveNote(db, note)) {
                    Toast.makeText(NoteActivity.this, "Note saved",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NoteActivity.this, "Issues while saving the note",
                            Toast.LENGTH_SHORT).show();
                }
                startActivity(getSupportParentActivityIntent());
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 002 02 Jan 18 remove current activity from stack
                Log.i(TAG, "delete pressed");
                if (DbUtils.deleteNote(db, note)) {
                    Toast.makeText(NoteActivity.this, "Note deleted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NoteActivity.this, "Issues while deleting the note",
                            Toast.LENGTH_SHORT).show();
                }
                startActivity(getSupportParentActivityIntent());
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 002 02 Jan 18 remove current activity from stack
                Log.i(TAG, "cancel pressed");
                startActivity(getSupportParentActivityIntent());
            }
        });


        // Hide save button at the opening
        saveButton.setVisibility(View.GONE);


        // Hide delete button if note doesn't exist
        if (!exists) deleteButton.setVisibility(View.GONE);


    }

    // Create text watcher for edit text field or Text Editor
    private TextWatcher makeTextWatcher(final String section, @Nullable final EditText editText,
                                        @Nullable final TextEditor textEditor) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (section.equals("Name") && editText != null) { // This is name field
                    note.setUpdatedName(editText.getText().toString());
                } else if (section.equals("Content") && textEditor != null) { // This is content field
                    note.setUpdatedContent(textEditor.getEditText());
                } else if (section.equals("Comment") && textEditor != null) { // This is comment field
                    note.setUpdatedComment(textEditor.getEditText());
                } else if (section.equals("Tags") && textEditor != null) { // This is tags field
                    note.setUpdatedTags(textEditor.getEditText());
                } else { // Error: Field is not defined
                    Log.e(TAG, "Error: edit text field could not be defined");
                }
                note.updateReadiness();
                showSaveButtonIfReady();
                Log.i(TAG, "Text changed in section: " + section);
            }
        };
    }


    // Decide if there is need to display save button
    void showSaveButtonIfReady() {
        if (note.isReadyForSave()) { // Show save button
            saveButton.setVisibility(View.VISIBLE);
        } else { // Hide save button
            saveButton.setVisibility(View.GONE);
        }
    }

}
