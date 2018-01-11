package com.example.android.introvert.Editors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android.introvert.Notes.Note;
import com.example.android.introvert.R;

/**
 * Created by takeoff on 008 08 Jan 18.
 */

public class TextEditor extends RelativeLayout implements MyEditor {

    String TAG = "INTROWERT_TEXT_EDITOR:";

    private EditText editText;

    private int role; // 1 - content, 2 - tags, 3 - comment

    public TextEditor(Context context){
        super(context);
    }

    public TextEditor(LinearLayout editorContainer, int editorType, int editorRole, boolean exists,
                      Note note, Activity activity) {
        super(activity);
        this.role = role;
        initComponent();

    }


    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.editor_text, this);
        editText = (EditText) findViewById(R.id.editor_text_content_et);
        editText.setTextColor(Color.BLACK);
    }


    public void addListener(TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

    public void setEditTextHint(String hint) {
        editText.setHint(hint);
    }



    public String typeAsString() {
        if (role == 1) return "Content";
        else if (role == 2) return "Tags";
        else if (role == 3) return "Comment";
        else return "Wrong role";
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public void deleteEditor() {

    }

    @Override
    public String getContent() {
        return null;
    }



    // Create text watcher for edit text field or Text MyEditor
 /*   private TextWatcher makeTextWatcher(int role) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
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
    }*/
}
