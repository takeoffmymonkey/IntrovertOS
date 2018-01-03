package com.example.android.introvert.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.introvert.Activities.MainActivity;
import com.example.android.introvert.Activities.NoteActivity;
import com.example.android.introvert.IntrovertDbHelper;
import com.example.android.introvert.R;
import com.example.android.introvert.Utils;

import static com.example.android.introvert.IntrovertDbHelper.NOTES_TABLE_NAME;
import static com.example.android.introvert.IntrovertDbHelper.SETTINGS_TABLE_NAME;
import static com.example.android.introvert.IntrovertDbHelper.TYPE_SETTINGS_TABLE_NAME;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class TimelineFragment extends Fragment {

    String TAG = "INTROWERT_TIMELINE:";

    MainActivity main;
    private SQLiteDatabase db;
    private IntrovertDbHelper dbHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        db = main.db;
        dbHelper = main.dbHelper;

        View timelineView = inflater.inflate(R.layout.fragment_timeline, container,
                false);

        //Return inflated layout for this fragment
        return timelineView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_timeline, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_timeline_dump_notes:
                Utils.dumpTableExternal(db, NOTES_TABLE_NAME);
                return true;

            case R.id.menu_timeline_dump_settings:
                Utils.dumpTableExternal(db, SETTINGS_TABLE_NAME);
                return true;

            case R.id.menu_timeline_dump_type_settings:
                Utils.dumpTableExternal(db, TYPE_SETTINGS_TABLE_NAME);
                return true;

            case R.id.menu_timeline_add_note:
                Intent intent = new Intent(getContext(), NoteActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

}
