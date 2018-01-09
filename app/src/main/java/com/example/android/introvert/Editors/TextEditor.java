package com.example.android.introvert.Editors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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

    public void setEditTextHint(String hint) {
        editText.setHint(hint);
    }

    public void setEditTextHintColor(int color) {
        editText.setHintTextColor(color);
    }


    public void setEditTextText(String text) {
        editText.setText(text);
    }

    public void setEditTextColor(int color) {
        editText.setTextColor(color);
    }

    public void setEditTextInputType(int type) {
        editText.setInputType(type);
    }

    public void setEditTextGravity(int gravity) {
        editText.setGravity(gravity);
    }

    public void setHeight(int pixels) {
        editText.setHeight(pixels);
    }

    public void setWidth(int pixels) {
        editText.setWidth(pixels);
    }

    public void setEditTextSize(int unit, float size) {
        editText.setTextSize(unit, size);
    }

    public void setEditTextTypeface(Typeface typeface) {
        editText.setTypeface(typeface);
    }

    public void setEditTextMinLines(int lines) {
        editText.setMinLines(lines);
    }

    public void setEditTextMaxines(int lines) {
        editText.setMaxLines(lines);
    }

    public String getEditText() {
        return editText.getText().toString();
    }

}
