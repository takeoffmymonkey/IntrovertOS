package com.example.android.introvert.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.introvert.Database.DbHelper;
import com.example.android.introvert.R;

import static com.example.android.introvert.Activities.MainActivity.db;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_CATEGORY_COLUMN;
import static com.example.android.introvert.Tabs.TimelineFragment.EXISTS;
import static com.example.android.introvert.Tabs.TimelineFragment.ID;

/**
 * Created by takeoff on 022 22 Jan 18.
 */

public class CategoriesActivity extends AppCompatActivity {
    private final String TAG = "INTROWERT_CATEGORIES_ACTIVITY";
    public static final String MODE = "mode";
    public static final String CHOICE = "choice";


    int currentMode = -1; // 0 - categories, 1 - subcategories, 2 - notes
    int currentChoice = -1; // !TABLE ID COLUMN VALUE of the chosen item

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Define currentMode we are in
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentMode = extras.getInt(MODE, -1);
            currentChoice = extras.getInt(CHOICE, -1);
        }

        // TODO: 022 22 Jan 18 i'm doing this fast, needs check
        ListView listView = (ListView) findViewById(R.id.a_categories_lv);

        Cursor cursor = null;
        String table;
        String[] columns = new String[2];
        columns[0] = DbHelper.ID_COLUMN;

        if (currentMode == 0) { // Categories
            table = DbHelper.CATEGORIES_TABLE_NAME;
            columns[1] = DbHelper.CATEGORIES_CATEGORY_COLUMN;
            cursor = db.query(table, columns, null, null,
                    null, null, null);
        } else if (currentMode == 1) { // Subcategories
            table = DbHelper.NOTE_TYPES_TABLE_NAME;
            columns[1] = DbHelper.NOTE_TYPES_DEFAULT_NAME_COLUMN;
            String selection = NOTE_TYPES_CATEGORY_COLUMN + "=?";
            String[] selectionArgs = {Integer.toString(currentChoice)};
            cursor = db.query(table, columns, selection, selectionArgs,
                    null, null, null);
        } else if (currentMode == 2) { // Notes
            table = DbHelper.NOTES_TABLE_NAME;
            columns[1] = DbHelper.NOTES_NAME_COLUMN;
            /*String selection = NOTE_TYPES_CATEGORY_COLUMN + "=?";
            String[] selectionArgs = {Integer.toString(currentChoice)};*/
            cursor = db.query(table, columns, null, null,
                    null, null, null);
        }

        final int[] ids = new int[cursor.getCount()];
        String[] names = new String[cursor.getCount()];

        int count = 0;
        while (cursor.moveToNext()) {
            ids[count] = cursor.getInt(cursor
                    .getColumnIndex(columns[0]));
            names[count] = cursor.getString(cursor
                    .getColumnIndex(columns[1]));
            count++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (currentMode == 0 || currentMode == 1) { // Need to continue using CategoriesActivity
                    intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                    intent.putExtra(CHOICE, ids[position]);
                    intent.putExtra(MODE, currentMode + 1);

                } else if (currentMode == 2) { // Open existing note via NoteActivity
                    intent = new Intent(getApplicationContext(), NoteActivity.class);
                    intent.putExtra(EXISTS, false);
                    intent.putExtra(ID, 0);
                }
                startActivity(intent);
            }
        });


        cursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_categories, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_category_add_new:
                Toast.makeText(this, "new", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
