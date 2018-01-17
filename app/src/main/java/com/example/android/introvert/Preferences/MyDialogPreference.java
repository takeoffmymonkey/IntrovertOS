package com.example.android.introvert.Preferences;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * Created by takeoff on 016 16 Jan 18.
 */

public class MyDialogPreference extends DialogPreference {
    private final String TAG = "INTROWERT_MY_DIALOG_PREFERENCE";

    public MyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        persistBoolean(positiveResult);
    }

}