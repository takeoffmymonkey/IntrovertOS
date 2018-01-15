package com.example.android.introvert.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.introvert.Adapters.CategoryAdapter;
import com.example.android.introvert.Database.DbHelper;
import com.example.android.introvert.R;
import com.example.android.introvert.Utils.FileUtils;

public class MainActivity extends AppCompatActivity {


    String TAG = "INTROWERT_MAIN:";
    public static DbHelper dbHelper;
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set defaults for main preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences_main, false);


        // Set activity's layout
        setContentView(R.layout.activity_main);


        // Get saved data
        if (savedInstanceState != null) {
        }


        // Setup tabs
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


        // Set app storage locations
        if (FileUtils.INTERNAL_APP_STORAGE == null)
            FileUtils.INTERNAL_APP_STORAGE = getFilesDir().toString();
        if (FileUtils.EXTERNAL_APP_STORAGE == null && FileUtils.externalStorageIsReady())
            FileUtils.EXTERNAL_APP_STORAGE = getExternalFilesDir(null).toString();
    }
}
