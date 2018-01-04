package com.example.android.introvert.Fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.introvert.Activities.MainActivity;
import com.example.android.introvert.Activities.NoteActivity;
import com.example.android.introvert.IntrovertDbHelper;
import com.example.android.introvert.NotesAdapter;
import com.example.android.introvert.Person;
import com.example.android.introvert.R;
import com.example.android.introvert.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.introvert.IntrovertDbHelper.NOTES_TABLE_NAME;
import static com.example.android.introvert.IntrovertDbHelper.NOTE_TYPES_TABLE_NAME;
import static com.example.android.introvert.IntrovertDbHelper.SETTINGS_TABLE_NAME;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class TimelineFragment extends Fragment {

    String TAG = "INTROWERT_TIMELINE:";

    MainActivity main;
    private SQLiteDatabase db;
    private IntrovertDbHelper dbHelper;


    private RecyclerView notesRecyclerView;
    private RecyclerView.LayoutManager notesLayoutManager;
    private RecyclerView.Adapter notesAdapter;


    private List<Person> persons;

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


        notesRecyclerView = (RecyclerView) timelineView.findViewById(R.id.fragment_timeline_rv);

        notesLayoutManager = new LinearLayoutManager(this.getContext());
        notesRecyclerView.setLayoutManager(notesLayoutManager);
        notesRecyclerView.setHasFixedSize(true);

        initializeData();

        notesAdapter = new NotesAdapter(persons);
        notesRecyclerView.setAdapter(notesAdapter);


        //Return inflated layout for this fragment
        return timelineView;

    }


    private void initializeData() {
        persons = new ArrayList<>();

        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.emma));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.emma));
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.emma));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.emma));
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.emma));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.emma));
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.emma));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.emma));
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.emma));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.emma));
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.emma));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.emma));


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

            case R.id.menu_timeline_add_note:
                Intent intent = new Intent(getContext(), NoteActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
