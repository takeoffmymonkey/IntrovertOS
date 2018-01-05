package com.example.android.introvert.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.introvert.Activities.MainActivity;
import com.example.android.introvert.Activities.NoteActivity;
import com.example.android.introvert.NotesCursorAdapter;
import com.example.android.introvert.R;
import com.example.android.introvert.Utils;

import static com.example.android.introvert.IntrovertDbHelper.INPUTS_TABLE_NAME;
import static com.example.android.introvert.IntrovertDbHelper.NOTES_TABLE_NAME;
import static com.example.android.introvert.IntrovertDbHelper.NOTE_TYPES_TABLE_NAME;
import static com.example.android.introvert.IntrovertDbHelper.SETTINGS_TABLE_NAME;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class TimelineFragment extends Fragment {

    String TAG = "INTROWERT_TIMELINE:";

    private SQLiteDatabase db = MainActivity.db;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View timelineView = inflater.inflate(R.layout.fragment_timeline, container, false);


        // Find the ListView which will be populated with the notes data
        ListView listListView = (ListView) timelineView.findViewById(R.id.fragment_timeline_list);


        Cursor cursor = db.query(NOTES_TABLE_NAME, null,
                null, null, null, null, null);


        NotesCursorAdapter listCursorAdapter = new NotesCursorAdapter(getContext(), cursor, 0);

        listListView.setAdapter(listCursorAdapter);


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

            case R.id.menu_timeline_dump_note_types:
                Utils.dumpTableExternal(db, NOTE_TYPES_TABLE_NAME);
                return true;

            case R.id.menu_timeline_dump_inputs:
                Utils.dumpTableExternal(db, INPUTS_TABLE_NAME);
                return true;


            case R.id.menu_timeline_add_note:
                Intent intent = new Intent(getContext(), NoteActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
