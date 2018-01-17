package com.example.android.introvert.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.introvert.Adapters.CategoryAdapter;
import com.example.android.introvert.Database.DbHelper;
import com.example.android.introvert.Preferences.MainPreferencesActivity;
import com.example.android.introvert.R;
import com.example.android.introvert.Utils.FileUtils;

import static com.example.android.introvert.Utils.FileUtils.EXTERNAL_APP_STORAGE;

public class MainActivity extends AppCompatActivity {


    private final String TAG = "INTROWERT_MAIN";

    public static final String ID = "id";
    public static final String EXISTS = "exists";

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
        if (EXTERNAL_APP_STORAGE == null && FileUtils.storageIsReady(null))
            EXTERNAL_APP_STORAGE = getExternalFilesDir(null).toString();


        // Temp: reading preferences example
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean m = sharedPreferences.getBoolean("preferences_main_debug_mode", false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_main_add_text_note:
                Intent intentAddTextNote = new Intent(this, NoteActivity.class);
                intentAddTextNote.putExtra(EXISTS, false);
                intentAddTextNote.putExtra(ID, 1);
                startActivity(intentAddTextNote);
                return true;

            case R.id.menu_main_add_audio_note:
                Intent intentAddAudioNote = new Intent(this, NoteActivity.class);
                intentAddAudioNote.putExtra(EXISTS, false);
                intentAddAudioNote.putExtra(ID, 2);
                startActivity(intentAddAudioNote);
                return true;

            case R.id.menu_main_preferences:
                Intent intentPreferences = new Intent(this, MainPreferencesActivity.class);
                startActivity(intentPreferences);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
