package com.example.android.introvert.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.introvert.Adapters.CategoryAdapter;
import com.example.android.introvert.Database.DbHelper;
import com.example.android.introvert.R;

public class MainActivity extends AppCompatActivity {


    String TAG = "INTROWERT_MAIN:";
    public static DbHelper dbHelper;
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set activity's layout
        setContentView(R.layout.activity_main);


        //Get saved data
        if (savedInstanceState != null) {
        }


        //Setup tabs
        CategoryAdapter categoryAdapter
                = new CategoryAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(categoryAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        //Get db access
        dbHelper = new DbHelper(this, DbHelper.DATABASE_NAME,
                null, DbHelper.DATABASE_VERSION);

        db = dbHelper.getReadableDatabase();

    }
}
