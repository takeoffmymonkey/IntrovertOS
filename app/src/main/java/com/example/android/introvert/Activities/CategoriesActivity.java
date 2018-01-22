package com.example.android.introvert.Activities;

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

/**
 * Created by takeoff on 022 22 Jan 18.
 */

public class CategoriesActivity extends AppCompatActivity {
    private final String TAG = "INTROWERT_CATEGORIES_ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // TODO: 022 22 Jan 18 i'm doing this fast, needs check 
        ListView listView = (ListView) findViewById(R.id.a_categories_lv);

        Cursor cursorCategories = db.query(DbHelper.CATEGORIES_TABLE_NAME, null, null, null,
                null, null, null);


        int[] categoryIds = new int[cursorCategories.getCount()];
        String[] categories = new String[cursorCategories.getCount()];

        int count = 0;
        while (cursorCategories.moveToNext()) {
            categoryIds[count] = cursorCategories.getInt(cursorCategories
                    .getColumnIndex(DbHelper.ID_COLUMN));
            categories[count] = cursorCategories.getString(cursorCategories
                    .getColumnIndex(DbHelper.CATEGORIES_CATEGORY_COLUMN));
            count++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, categories);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CategoriesActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

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
