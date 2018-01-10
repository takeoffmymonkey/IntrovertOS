package com.example.android.introvert.Editors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.android.introvert.R;

/**
 * Created by takeoff on 008 08 Jan 18.
 */

public class TextEditor extends RelativeLayout implements Editable {

    String TAG = "INTROWERT_TEXT_EDITOR:";

    private EditText editText;

    private int type; // 1 - content, 2 - tags, 3 - comment

    public TextEditor(Context context, int type) {
        super(context);
        this.type = type;
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

    public int getType() {
        return type;
    }

    public String typeAsString() {
        if (type == 1) return "Content";
        else if (type == 2) return "Tags";
        else if (type == 3) return "Comment";
        else return "Wrong type";
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public String getContent() {
        return null;
    }
}
