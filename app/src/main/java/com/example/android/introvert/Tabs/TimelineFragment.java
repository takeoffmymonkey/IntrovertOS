package com.example.android.introvert.Tabs;

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
import com.example.android.introvert.Adapters.NotesCursorAdapter;
import com.example.android.introvert.R;

import static com.example.android.introvert.Database.DbHelper.NOTES_TABLE_NAME;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class TimelineFragment extends Fragment {

    private final String TAG = "INTROWERT_TIMELINE_FRAGMENT";
    public static final String ID = "id";
    public static final String EXISTS = "exists";

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

            case R.id.menu_timelime_test_option:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
