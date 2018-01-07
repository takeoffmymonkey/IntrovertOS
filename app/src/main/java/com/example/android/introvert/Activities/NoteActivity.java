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
                // addOrUpdateNote();
                startActivity(getSupportParentActivityIntent());
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

        // Hide save button at the opening
        saveButton.setVisibility(View.GONE);

        // Hide delete button if note doesn't exist
        if (!exists) deleteButton.setVisibility(View.GONE);

        // Create name field, set content and change listener
        final EditText noteNameEditText = (EditText) findViewById(R.id.a_note_note_name_et);
        noteNameEditText.setText(note.getName());
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
                //setDirty(noteNameEditText);
            }
        });




/*
        final EditText noteContentEditText = (EditText) findViewById(R.id.a_note_note_text_et);


        noteContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "noteTextEditText changed");
                setDirty(noteContentEditText);
            }
        });
*/


    }

    /*
    private void maybeShowSaveButton() {
        // must be changed state
        if (isDirty) saveButton.setVisibility(View.VISIBLE);
        else saveButton.setVisibility(View.GONE);
    }


    //
    private void nameInitValue(EditText nameEditText) {
        String nameInitValue = null;

        if (activityMode == 1) { // we are in add new note mode
            // read default from db
            nameInitValue = DbUtils.getNameForNewNote(MainActivity.db, noteType);

        } else if (activityMode == 2) { // we are in edit mode
            // read for current noteId from db

        } else { // wrong activityMode value
            nameInitValue = "wrong activityMode value";
            Log.e(TAG, "Wrong activityMode value");
        }

        nameEditText.setText(nameInitValue);
        initValues.put(nameEditText, nameInitValue);
    }


    private void contentInitValue(EditText contentEditText) {
        String contentInitValue = null;

        if (activityMode == 1) { // we are in add new note mode
            // read default from db
            contentInitValue = "";

        } else if (activityMode == 2) { // we are in edit mode
            // read for current noteId from db

        } else { // wrong activityMode value
            contentInitValue = "wrong activityMode value";
            Log.e(TAG, "Wrong activityMode value");
        }

        contentEditText.setText(contentInitValue);
        initValues.put(contentEditText, contentInitValue);

    }


    private String getCurrentValue(View view) {
        String currentValue = "wrong value";

        // check the type of View
        if (view instanceof EditText) { // it is EditText
            EditText currentView = (EditText) view;
            return currentView.getText().toString();
        } else if (view instanceof Spinner) { // it is Spinner
            Spinner currentView = (Spinner) view;
            return currentView.toString();
        } else { // type not defined
            Log.e(TAG, "Wrong type for getting value from!");
            return currentValue;
        }

    }


    private boolean sameAsInitValue(View view) {
        // compare current value with init value
        return getCurrentValue(view).equals(initValues.get(view));
    }


    private void setDirty(View changedView) {
        // TODO: 003 03 Jan 18 make only name and content obligatory
        // TODO: 004 04 Jan 18 prevent inserting note with empty name/content 
        if (!getCurrentValue(changedView).equals("") &&
                !sameAsInitValue(changedView)) { // not empty and differs from init
            isDirty = true;
            if (!dirtyViews.contains(changedView)) dirtyViews.add(changedView);
            maybeShowSaveButton();

        } else { // either empty or same from init
            dirtyViews.remove(changedView);
            // set is changed to false if dirtyViews is empty
            if (dirtyViews.size() == 0) {
                isDirty = false;
            }
            maybeShowSaveButton();
        }
    }


    private void addOrUpdateNote(@Nullable Integer id, View... views) {
        ContentValues contentValues = new ContentValues();

        if (activityMode == 1 && id == -1) { // we are in add activityMode
            contentValues.put(NOTES_TYPE_COLUMN, noteType);
            contentValues.put(NOTES_NAME_COLUMN, getCurrentValue(views[0]));
            contentValues.put(NOTES_CONTENT_COLUMN, getCurrentValue(views[1]));

            if (MainActivity.db.insert(NOTES_TABLE_NAME, null,
                    contentValues) == -1) {
                Log.e(TAG, "ERROR INSERTING");
            }

        } else if (activityMode == 2 && id != -1) { // we are in edit activityMode

            *//*contentValues.put(DbHelper.SETTINGS_1_COLUMN, 0);
            contentValues.put(DbHelper.SETTINGS_2_COLUMN, 0);
*//*
            if (MainActivity.db.update(NOTES_TABLE_NAME, contentValues,
                    DbHelper.ID_COLUMN + "=?",
                    new String[]{"1"}) == -1) {
                Log.e(TAG, "ERROR UPDATING");
            }

        } else { // error: probably activityMode var was not initialized properly
            Log.e(TAG, "Error: wrong activityMode value! " +
                    "Probably activityMode variable was not initialized properly");
        }
    }

*/
}
