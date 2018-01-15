package com.example.android.introvert.Preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.introvert.R;

public class MainPreferencesFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    // Register listener for SharedPreferences
    SharedPreferences sharedPref;

    SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                    // listener implementation
                    Log.i(TAG, "preference changed 11");
                }
            };


    String TAG = "INTROWERT_MAIN_PREFERENCES_FRAGMENT:";

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