package com.example.android.introvert.Editors;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.android.introvert.R;

/**
 * Created by takeoff on 008 08 Jan 18.
 */

public class TextEditor extends RelativeLayout {

    String TAG = "INTROWERT_TEXT_EDITOR:";

    private EditText editText;

    public TextEditor(Context context) {
        super(context);
        initComponent();
    }


    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.editor_text, this);
        editText = (EditText) findViewById(R.id.editor_text_content_et);
        editText.setTextColor(Color.BLACK);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "edit text changed");
            }
        });

    }


    public void setEditText(String name) {
        editText.setText(name);

    }

    public String getEditText() {
        return editText.getText().toString();
    }

}
