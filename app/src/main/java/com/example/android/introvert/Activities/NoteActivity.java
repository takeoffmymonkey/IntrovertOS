package com.example.android.introvert.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.introvert.R;

/**
 * Created by takeoff on 029 29 Dec 17.
 */

public class NoteActivity extends AppCompatActivity {

    String TAG = "INTROWERT_NOTE:";
    int mode = -1; // 1 - add mode; 2 - edit mode

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mode = 1;

        EditText noteNameEditText = (EditText) findViewById(R.id.a_note_note_name_et);
        EditText noteTextEditText = (EditText) findViewById(R.id.a_note_note_text_et);

        Button deleteButton = (Button) findViewById(R.id.a_note_delete_b);
        Button saveButton = (Button) findViewById(R.id.a_note_save_b);
        Button cancelButton = (Button) findViewById(R.id.a_note_cancel_b);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 002 02 Jan 18 remove current activity from stack 
                startActivity(getSupportParentActivityIntent());
            }
        });


        if (mode == 1) { // we are in add mode
            deleteButton.setVisibility(View.GONE);
        } else if (mode == 2) { // we are in edit mode
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } else { // error: probably var was not initialized properly
            Log.e(TAG, "Error: wrong mode value! " +
                    "Probably mode variable was not initialized properly");
        }
    }

    private String getEditTextContent(EditText editText) {
        return editText.getText().toString();
    }


    private Boolean updateField(int id, String... columns) {
        return true;
    }

}
