package com.example.android.introvert.Activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.introvert.IntrovertDbHelper;
import com.example.android.introvert.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.android.introvert.IntrovertDbHelper.NOTES_TABLE_NAME;

/**
 * Created by takeoff on 029 29 Dec 17.
 */

public class NoteActivity extends AppCompatActivity {

    String TAG = "INTROWERT_NOTE:";
    int mode = -1; // 1 - add mode; 2 - edit mode
    boolean isChanged = false; // changes to any fields

    Button saveButton;
    Button deleteButton;
    Button cancelButton;

    ArrayList<View> changedViews = new ArrayList<>();
    HashMap<View, String> initValues;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mode = 1;

        saveButton = (Button) findViewById(R.id.a_note_save_b);
        deleteButton = (Button) findViewById(R.id.a_note_delete_b);
        cancelButton = (Button) findViewById(R.id.a_note_cancel_b);

        showDeleteButton();

        // TODO: 002 02 Jan 18 Prevent multiple rows
        final EditText noteNameEditText = (EditText) findViewById(R.id.a_note_note_name_et);
        final EditText noteTextEditText = (EditText) findViewById(R.id.a_note_note_text_et);

        // Set and save init values
        setInitValues(noteNameEditText, noteTextEditText);


        // Set listeners
        noteNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) { // Text changed
                Log.i(TAG, "noteNameEditText changed");
                setChanged(noteNameEditText);
            }
        });

        noteTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "noteTextEditText changed");
                setChanged(noteTextEditText);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "save pressed");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "delete pressed");
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


    }


    private String getEditTextContent(EditText editText) {
        return editText.getText().toString();
    }


    private Boolean addOrUpdateNote(@Nullable Integer id) {
        ContentValues contentValues = new ContentValues();

        if (mode == 1 && id == null) { // we are in add mode
            contentValues.put(IntrovertDbHelper.SETTINGS_1_COLUMN, 2);

            if (MainActivity.db.insert(NOTES_TABLE_NAME, null,
                    contentValues) == -1) {
                Log.e(TAG, "ERROR INSERTING");
            }

        } else if (mode == 2 && id != null) { // we are in edit mode

            contentValues.put(IntrovertDbHelper.SETTINGS_1_COLUMN, 0);
            contentValues.put(IntrovertDbHelper.SETTINGS_2_COLUMN, 0);

            if (MainActivity.db.update(NOTES_TABLE_NAME, contentValues,
                    IntrovertDbHelper.ID_COLUMN + "=?",
                    new String[]{"1"}) == -1) {
                Log.e(TAG, "ERROR UPDATING");
            }

        } else { // error: probably mode var was not initialized properly
            Log.e(TAG, "Error: wrong mode value! " +
                    "Probably mode variable was not initialized properly");
        }

        return true;
    }


    private void showSaveButton() {
        // must be changed state
        if (isChanged) saveButton.setVisibility(View.VISIBLE);
        else saveButton.setVisibility(View.GONE);
    }


    private void showDeleteButton() {
        // must be in edit mode
        if (mode == 1) deleteButton.setVisibility(View.GONE);// add mode
        else if (mode == 2) deleteButton.setVisibility(View.VISIBLE); // edit mode
        else { // error: probably mode var was not initialized properly
            Log.e(TAG, "Error: wrong mode value! " +
                    "Probably mode variable was not initialized properly");
        }
    }


    private void setChanged(View changedView) {
        // at least 1 field should differ from its initial value
        // name and content field should not be empty

        isChanged = true;
        showSaveButton();
        if (!changedViews.contains(changedView)) changedViews.add(changedView);
    }


    private void setInitValues(View... views) {
        // check current mode
        // for add mode set default type values
        // for edit mode read values from db
        // save init values to initValues hashmap

        // check current mode
        if (mode == 1) {// add mode - set default type values

        } else if (mode == 2) {// edit mode - read values from db

        } else { // error: probably mode var was not initialized properly
            Log.e(TAG, "Error: wrong mode value! " +
                    "Probably mode variable was not initialized properly");
        }

        // Save values to initValues hashmap

    }

    private boolean sameAsInitValue(View view) {
        // compare current value with init value

        return false;
    }
}
