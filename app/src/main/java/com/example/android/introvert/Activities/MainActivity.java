package com.example.android.introvert.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.introvert.CategoryAdapter;
import com.example.android.introvert.IntrovertDbHelper;
import com.example.android.introvert.R;

public class MainActivity extends AppCompatActivity {


    String TAG = "INTROWERT_MAIN:";
    public IntrovertDbHelper dbHelper;
    public SQLiteDatabase db;

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
        dbHelper = new IntrovertDbHelper(this, IntrovertDbHelper.DATABASE_NAME,
                null, IntrovertDbHelper.DATABASE_VERSION);

        db = dbHelper.getReadableDatabase();

    }
}
