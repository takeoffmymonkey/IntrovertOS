package com.example.android.introvert.Editors;

import android.content.Context;
import android.graphics.Color;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android.introvert.Activities.NoteActivity;
import com.example.android.introvert.Notes.Note;
import com.example.android.introvert.R;

/**
 * Created by takeoff on 008 08 Jan 18.
 */

public class TextEditor extends RelativeLayout implements MyEditor {

    private final String TAG = "INTROWERT_TEXT_EDITOR:";

    // Which type of content will be used: 1 - text, 2 - audio, 3 - video, 4 - photo, 5 - image
    private int editorType = 0;
    // What is the role (context) in which editor is used: 1 - content, 2 - tags, 3 - comment
    private int editorRole = 0;
    // Whether the note exist and its content should be used, or created empty
    private boolean exists;
    // Note for which editor is created
    private Note note;
    // Key edit text UI element of the Editor
    private EditText editText;
    // Activity where editor is used
    private NoteActivity noteActivity;


    /* Basic required constructor */
    private TextEditor(Context context) {
        super(context);
    }


    /* Regular constructor */
    public TextEditor(LinearLayout editorContainer, int editorType, int editorRole, boolean exists,
                      Note note, NoteActivity noteActivity) {
        this(noteActivity);

        this.editorType = editorType;
        this.editorRole = editorRole;
        this.exists = exists;
        this.note = note;
        this.noteActivity = noteActivity;

        initComponents();
    }


    /* Initialize layout and its components */
    private void initComponents() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
            inflater.inflate(R.layout.editor_text, this);
        } else {
            Log.e(TAG, "Error: could not find editor_text view!");
        }

        // Set up edit text field
        editText = (EditText) findViewById(R.id.editor_text_content_et);
        editText.setTextColor(Color.BLACK);
        editText.setHint("Enter your text here");
        editText.addTextChangedListener(makeTextWatcher());
        if (exists) { // Set text if note exists
            switch (editorRole) {
                case 1: // Content
                    editText.setText(note.getInitContent());
                    break;
                case 2: // Tags
                    editText.setText(note.getInitTags());
                    break;
                case 3: // Comment
                    editText.setText(note.getInitComment());
                    break;
                default: // Something is wrong
                    Log.e(TAG, "Error: editor role could not be defined when setting init text");
            }
        }
    }


    /* Create text watcher for edit text field or Text MyEditor */
    private TextWatcher makeTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                switch (editorRole) {
                    case 1: // Content
                        note.setUpdatedContent(editText.getText().toString());
                        note.updateReadiness();
                        noteActivity.showSaveButtonIfReady();
                        break;
                    case 2: // Tags
                        note.setUpdatedTags(editText.getText().toString());
                        note.updateReadiness();
                        noteActivity.showSaveButtonIfReady();
                        break;
                    case 3: // Comment
                        note.setUpdatedComment(editText.getText().toString());
                        note.updateReadiness();
                        noteActivity.showSaveButtonIfReady();
                        break;
                    default: // Something is wrong
                        Log.e(TAG, "Error: editor role could not be defined when changing text");
                        note.updateReadiness();
                        noteActivity.showSaveButtonIfReady();
                        break;
                }
            }
        };
    }


    /* Interface methods */
    @Override
    public void setContent(String content) {
        editText.setText(content);
        switch (editorRole) {
            case 1: // Content
                note.setUpdatedContent(content);
                break;
            case 2: // Tags
                note.setUpdatedTags(content);
                break;
            case 3: // Comment
                note.setUpdatedComment(content);
                break;
            default: // Something is wrong
                Log.e(TAG, "Error: editor role could not be defined when setting content");
        }
    }

    @Override
    public String getContent() {
        return editText.getText().toString();
    }

    @Override
    public int getEditorType() {
        return editorType;
    }

    @Override
    public int getEditorRole() {
        return editorRole;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public boolean deleteEditor() {
        return false;
    }
}
