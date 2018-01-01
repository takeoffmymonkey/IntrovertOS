package com.example.android.introvert.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.introvert.Activities.MainActivity;
import com.example.android.introvert.Activities.NoteActivity;
import com.example.android.introvert.IntrovertDbHelper;
import com.example.android.introvert.R;
import com.example.android.introvert.Utils;

import static com.example.android.introvert.IntrovertDbHelper.SETTINGS_TABLE_NAME;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class TimelineFragment extends Fragment {

    String TAG = "INTROWERT_TIMELINE:";

    MainActivity main;
    private SQLiteDatabase db;
    private IntrovertDbHelper dbHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        db = main.db;
        dbHelper = main.dbHelper;

        View timelineView = inflater.inflate(R.layout.fragment_timeline, container,
                false);

        Button b = (Button) timelineView.findViewById(R.id.add_note);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoteActivity.class);
                startActivity(intent);
                putInDb();
                new DumpTable(db, SETTINGS_TABLE_NAME).execute();

            }
        });

        //Return inflated layout for this fragment
        return timelineView;

    }


    void putInDb() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IntrovertDbHelper.SETTINGS_1_COLUMN, 2);
        contentValues.put(IntrovertDbHelper.SETTINGS_2_COLUMN, 4);

        if (db.insert(SETTINGS_TABLE_NAME, null,
                contentValues) == -1) {
            Log.e(TAG, "ERROR INSERTING");
        }

    }

    void putOutDb() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IntrovertDbHelper.SETTINGS_1_COLUMN, 0);
        contentValues.put(IntrovertDbHelper.SETTINGS_2_COLUMN, 0);

        if (db.update(SETTINGS_TABLE_NAME, contentValues,
                IntrovertDbHelper.ID_COLUMN + "=?",
                new String[]{"1"}) == -1) {
            Log.e(TAG, "ERROR DELETING");
        }
    }


    public static class DumpTable extends AsyncTask<String, Void, Boolean> {
        SQLiteDatabase db;
        String tableName;

        DumpTable(SQLiteDatabase db, String tableName) {
            this.db = db;
            this.tableName = tableName;
        }

        protected Boolean doInBackground(String... params) {
            Utils.dumpTable(db, tableName);
            return true;
        }

        protected void onPostExecute() {
        }
    }


}
