package com.example.android.introvert.Preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.introvert.Activities.MainActivity;
import com.example.android.introvert.R;
import com.example.android.introvert.Utils.DbUtils;

import static com.example.android.introvert.Activities.MainActivity.db;
import static com.example.android.introvert.Database.DbHelper.CATEGORIES_TABLE_NAME;
import static com.example.android.introvert.Database.DbHelper.INPUT_TYPES_TABLE_NAME;
import static com.example.android.introvert.Database.DbHelper.NOTES_TABLE_NAME;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_TABLE_NAME;
import static com.example.android.introvert.Utils.FileUtils.EXTERNAL_APP_STORAGE;
import static com.example.android.introvert.Utils.FileUtils.EXTERNAL_STORAGE;
import static com.example.android.introvert.Utils.FileUtils.SD_STORAGE;
import static com.example.android.introvert.Utils.FileUtils.storageIsReady;

public class MainPreferencesFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    String TAG = "INTROWERT_MAIN_PREFERENCES_FRAGMENT:";


    SharedPreferences sharedPref;

    SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                    if (key.equals("preferences_main_audio_notes_location")) {
                        // user changed text location table option
                        ListPreference locations = (ListPreference) findPreference(key);
                        String locationValue = locations.getValue();
                        switch (locationValue) {
                            case "internal_app_storage":
                                DbUtils.setInputTypesContentLocation
                                        (MainActivity.db, 1, 1);
                                break;
                            case "external_app_storage":
                                if (storageIsReady(EXTERNAL_APP_STORAGE)) { // external storage is ready
                                    DbUtils.setInputTypesContentLocation
                                            (MainActivity.db, 1, 2);
                                } else { // external storage is NOT ready
                                    Log.e(TAG, "Error: External storage is not ready");
                                }
                                break;
                            case "external_storage":
                                if (storageIsReady(EXTERNAL_STORAGE)) { // external storage is ready
                                    DbUtils.setInputTypesContentLocation
                                            (MainActivity.db, 1, 3);
                                } else { // external storage is NOT ready
                                    Log.e(TAG, "Error: External storage is not ready");
                                }
                                break;
                            case "sd_storage":
                                if (storageIsReady(SD_STORAGE)) { // SD card storage exists
                                    DbUtils.setInputTypesContentLocation
                                            (MainActivity.db, 1, 4);
                                } else { // SD card storage DOES NOT exist
                                    Log.e(TAG, "Error: SD Card is not ready");
                                }
                                break;
                        }
                    } else if (key.equals("preferences_main_dump_table")) {
                        // user changed dump table option
                        ListPreference dumpTable = (ListPreference) findPreference(key);

                        switch (dumpTable.getValue()) {
                            case "NOTES":
                                DbUtils.dumpTableExternal(db, NOTES_TABLE_NAME);
                                break;
                            case "NOTE_TYPES":
                                DbUtils.dumpTableExternal(db, NOTE_TYPES_TABLE_NAME);
                                break;
                            case "INPUT_TYPES":
                                DbUtils.dumpTableExternal(db, INPUT_TYPES_TABLE_NAME);
                                break;
                            case "CATEGORIES":
                                DbUtils.dumpTableExternal(db, CATEGORIES_TABLE_NAME);
                                break;
                        }
                    }
                }
            };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity()
                .getApplicationContext());

        // Load the preferences_main from an XML resource
        addPreferencesFromResource(R.xml.preferences_main);
    }


    @Override
    public void onResume() {
        super.onResume();
        sharedPref.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPref.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // required method, but actual implementation is for listener object above
        // Guide: Внимание! Когда вы вызываете приемник registerOnSharedPreferenceChangeListener(),
        // диспетчер предпочтений не сохраняет строгую ссылку на приемник. Вы должны сохранить
        // строгую ссылку на приемник, в противном случае она будет чувствительной к очистке памяти.
        // Мы рекомендуем хранить ссылку на приемник в данных экземпляра объекта , который будет
        // существовать, пока вам нужен приемник.
    }

}