package com.example.android.introvert.Preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.android.introvert.R;

public class MainPreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

}