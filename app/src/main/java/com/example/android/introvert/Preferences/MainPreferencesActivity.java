package com.example.android.introvert.Preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by takeoff on 015 15 Jan 18.
 */

public class MainPreferencesActivity extends AppCompatActivity {

    // TODO: 015 15 Jan 18 use headers instead of categories https://developer.android.com/guide/topics/ui/settings.html#PreferenceHeaders

    String TAG = "INTROWERT_MAIN_PREFERENCES_ACTIVITY:";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainPreferencesFragment())
                .commit();
    }


}
