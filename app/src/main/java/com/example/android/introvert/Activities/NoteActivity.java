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
import com.example.android.introvert.Editors.AudioEditor;
import com.example.android.introvert.Editors.ImageEditor;
import com.example.android.introvert.Editors.MyEditor;
import com.example.android.introvert.Editors.PhotoEditor;
import com.example.android.introvert.Editors.TextEditor;
import com.example.android.introvert.Editors.VideoEditor;
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

    MyEditor contentEditor;
    MyEditor tagsEditor;
    MyEditor commentEditor;


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


        // Create containers for editors
        LinearLayout contentEditorContainer
                = (LinearLayout) findViewById(R.id.a_note_content_editor_container);
        LinearLayout tagsEditorContainer
                = (LinearLayout) findViewById(R.id.a_note_tags_editor_container);
        LinearLayout commentEditorContainer
                = (LinearLayout) findViewById(R.id.a_note_comment_editor_container);


        // Create name field, set content and change listener
        TextView noteNameTextView = (TextView) findViewById(R.id.a_note_note_name_tv);
        final EditText noteNameEditText = (EditText) findViewById(R.id.a_note_note_name_et);
        noteNameEditText.setText(note.getInitName());
        noteNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setUpdatedName(noteNameEditText.getText().toString());
                note.updateReadiness();
                showSaveButtonIfReady();
            }
        });


        // Add content, tags and comment text views
        TextView noteContentTextView = (TextView) findViewById(R.id.a_note_note_content_tv);
        TextView noteTagsTextView = (TextView) findViewById(R.id.a_note_note_tags_tv);
        TextView noteCommentTextView = (TextView) findViewById(R.id.a_note_note_comment_tv);


        // Add content, tags and comment editors
        contentEditor = makeEditor(contentEditorContainer, note.getInitContentInputType(),
                1, exists, note, this);
        tagsEditor = makeEditor(contentEditorContainer, note.getInitTagsInputType(),
                2, exists, note, this);
        commentEditor = makeEditor(commentEditorContainer, note.getInitCommentInputType(),
                3, exists, note, this);


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


    // Decide if there is need to display save button
    public void showSaveButtonIfReady() {
        if (note.isReadyForSave()) { // Show save button
            saveButton.setVisibility(View.VISIBLE);
        } else { // Hide save button
            saveButton.setVisibility(View.GONE);
        }
    }


    /**
     * Creates and returns a specified type editor for a specified View group container
     *
     * @param editorContainer View container (Linear Layout) where editor View elements will be added
     * @param editorType      Int with type of the requested editor (1 - text, 2 - audio, 3 - video,
     *                        4 - photo, 5 - image)
     * @param editorRole      Int specifying context for the use of editor (1 - content editor, 2 -
     *                        tags editor, 3 - comment editor)
     * @param exists          Boolean to specify if this is an empty editor (for a new {@link Note}),
     *                        or it contains existing content
     * @param note            Note object (either fresh or existing), for which editor is created
     * @param noteActivity    Link to current NoteActivity. Required for updating UI elements of
     *                        editor, such as scroll bar, or providing context for layout.
     * @return MyEditor interface
     */
    MyEditor makeEditor(LinearLayout editorContainer, int editorType, int editorRole, boolean exists,
                        Note note, NoteActivity noteActivity) {

        switch (editorType) {

            default:
            case 1: // Text editor
                TextEditor textEditor = new TextEditor(editorContainer, editorType, editorRole, exists,
                        note, noteActivity);
                editorContainer.addView(textEditor);
                return textEditor;
            case 2: // Audio editor
                AudioEditor audioEditor = new AudioEditor(editorContainer, editorType, editorRole,
                        exists, note, noteActivity);
                editorContainer.addView(audioEditor);
                return audioEditor;
            case 3: // Video editor
                VideoEditor videoEditor = new VideoEditor(editorContainer, editorType, editorRole,
                        exists, note, noteActivity);
                editorContainer.addView(videoEditor);
                return videoEditor;
            case 4: // Photo editor
                PhotoEditor photoEditor = new PhotoEditor(editorContainer, editorType, editorRole,
                        exists, note, noteActivity);
                editorContainer.addView(photoEditor);
                return photoEditor;
            case 5: // Image editor
                ImageEditor imageEditor = new ImageEditor(editorContainer, editorType, editorRole,
                        exists, note, noteActivity);
                editorContainer.addView(imageEditor);
                return imageEditor;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        contentEditor.deleteEditor();
        tagsEditor.deleteEditor();
        commentEditor.deleteEditor();
        // TODO: 010 10 Jan 18 handler.removeCallbacks(runnable)
    }
}
