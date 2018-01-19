package com.example.android.introvert.Utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by takeoff on 019 19 Jan 18.
 */

public class FormatUtils {
    private static final String TAG = "INTROWERT_FORMAT_UTILS";


    /* Formats milliseconds to 00:00 */
    public static String msToMinsAndSecs(int millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
