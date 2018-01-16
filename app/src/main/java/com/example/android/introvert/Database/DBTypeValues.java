package com.example.android.introvert.Database;

/**
 * Created by takeoff on 003 03 Jan 18.
 */

public class DBTypeValues {

    static private String TAG = "INTROWERT_DBTYPEVALUES:";

    static final byte[] priorities = {1, 2, 3, 4, 5};

    public static final String[] inputTypes = {
            "text",
            "audio",
            "video",
            "photo",
            "picture"
    };

    public static final String[] contentLocations = {
            "db_storage",
            "internal_app_storage",
            "external_app_storage",
            "external_storage",
            "sd_storage"
    };

    public static final String[] defaultCategories = {
            "Ideas",
            "Jokes",
            "Tracks",
            "Videos",
            "Photos",
            "Spendings"
    };
}
