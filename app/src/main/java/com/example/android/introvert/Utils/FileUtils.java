package com.example.android.introvert.Utils;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.introvert.Database.DBTypeValues;

import java.io.File;

import static com.example.android.introvert.Database.DbHelper.CATEGORIES_CATEGORY_COLUMN;
import static com.example.android.introvert.Database.DbHelper.CATEGORIES_TABLE_NAME;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_CATEGORY_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_DEFAULT_NAME_COLUMN;
import static com.example.android.introvert.Database.DbHelper.NOTE_TYPES_TABLE_NAME;

/**
 * Created by takeoff on 009 09 Jan 18.
 */

public class FileUtils {
    private static final String TAG = "INTROWERT_UTILS";

    /* Android doesn't separate external SD card from external storage, which in some cases may be
    *  an internal card. In such cases methods like getExternalStorageDirectory()... will return
    *  info about the internal card and cannot be applied to the actual external SD card!
    *
    *  In my case getExternalStorageDirectory() returns /storage/sdcard0 which is an INTERNAL card.
    *  Meanwhile the actual external SD card has path on my device /storage/sdcard1 and I don't know
    *  how to define this path dynamically.
    *
    *  Also, the internal app storage path method Context.getFilesDir() returns:
    *  >> /data/data/com.example.android.introvert/files
    *  while external app storage path method getExternalFilesDir(null) returns:
    *  >> /storage/sdcard0/Android/data/com.example.android.introvert/files
    *  These 2 paths I suppose should work device-agnostic. Files from these 2 folders are removed
    *  during uninstall, app doesn't require any permission to access them and files here are not
    *  visible to media scanner. But! files on getExternalFilesDir(null) still can be accessed by
    *  any app with read or write permission, while files under getFilesDir() can be hidden
    *  to both user and other apps (which is how it works by default).
    *
    *  Also, quoting API guide: "Sometimes, a device that has allocated a partition of the internal
    *  memory for use as the external storage may also offer an SD card slot. When such a device is
    *  running Android 4.3 and lower, the getExternalFilesDir() method provides access to only the
    *  internal partition and your app cannot read or write to the SD card. Beginning with Android
    *  4.4, however, you can access both locations by calling getExternalFilesDirs(), which returns
    *  a File array with entries each location. The first entry in the array is considered the
    *  primary external storage and you should use that location unless it's full or unavailable."
    *
    *  Ok, trying this method on Android 4.4 and it returns an array of 1 file only, which is still:
    *  >> /storage/sdcard0/Android/data/com.example.android.introvert/files
    *
    *  More info and possible kostili here:
    *  https://stackoverflow.com/questions/5694933/find-an-external-sd-card-location/5695129#5695129
    *
    *  Full mapping in my case:
    *  >> /data/data/com.example.android.introvert/files == internal app storage
    *  >> /storage/sdcard0/Android/data/com.example.android.introvert/files = external app storage
    *  >> /storage/sdcard0 == /mnt/sdcard == internal card storage
    *  >> /storage/sdcard1 == /mnt/sdcard2 == sd card storage
    * */

    // TODO: 012 12 Jan 18 Make defining storage dynamic.

    // /data/data/com.example.android.introvert/files
    public static String INTERNAL_APP_STORAGE = null;
    // /storage/sdcard0/Android/data/com.example.android.introvert/files
    public static String EXTERNAL_APP_STORAGE = null;
    // /storage/sdcard0
    public static final String EXTERNAL_STORAGE = getAbsolutePathOfFile(getExternalStorageRootDir());
    public static final String SD_STORAGE = "/storage/sdcard1";


    /* Checks if storage is mounted */
    public static boolean storageIsReady(@Nullable String storage) {
        if (storage == null || storage.equals(EXTERNAL_APP_STORAGE) ||
                storage.equals(EXTERNAL_STORAGE))
            // null is for initializing; all cases should perform same check
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        else if (storage.equals(INTERNAL_APP_STORAGE)) {
            // internal storage should always true
            return true;
        } else if (storage.equals(SD_STORAGE)) {
            // SD card should be checked on existance
            return makeFileForPath(SD_STORAGE).exists();
        } else {
            Log.e(TAG, "This location should not exist");
            return false;
        }
    }


    /* Returns root directory of external storage drive */
    private static File getExternalStorageRootDir() {
        if (storageIsReady(EXTERNAL_STORAGE)) return Environment.getExternalStorageDirectory();
        else return null;
    }


    /* Returns absolute path of file as String */
    public static String getAbsolutePathOfFile(File file) {
        if (file.exists()) return file.getAbsolutePath();
        else return "File doesn't exist";
    }


    /* Returns file for specified path */
    public static File makeFileForPath(String path) {
        return new File(path);
    }


    /* Deletes file */
    public static boolean deleteFile(File file) {
        return file.exists() && file.delete();
    }


    /* Creates the directory named by this abstract pathname, including any necessary but
    nonexistent parent directories. */
    public static boolean mkDirs(File file) {
        return file.mkdirs();
    }


    /**
     * Creates folders structure for input type
     *
     * @return <code>true</code> if and only if the directory was created,
     * along with all necessary parent directories; <code>false</code>
     * otherwise
     */
    public static boolean makeDirsForInputType(String inputType, int noteType) {

        // Get root location for the specified type
        String rootContentLocation = DbUtils.getRootContentLocationCodeForInputType(inputType);
        if (rootContentLocation.equals(DBTypeValues.contentLocationCodes[1])) {
            // internal_app_storage
            rootContentLocation = INTERNAL_APP_STORAGE;
        } else if (rootContentLocation.equals(DBTypeValues.contentLocationCodes[2])) {
            // external_app_storage
            rootContentLocation = EXTERNAL_APP_STORAGE;
        } else if (rootContentLocation.equals(DBTypeValues.contentLocationCodes[3])) {
            // external_storage
            rootContentLocation = EXTERNAL_STORAGE;
        } else if (rootContentLocation.equals(DBTypeValues.contentLocationCodes[4])) {
            // sd_storage
            rootContentLocation = SD_STORAGE;
        } else { // Match for code isn't found
            Log.e(TAG, "Couldn't find match for content location code: " + rootContentLocation);
        }

        // Get category name for such type data
        int categoryId = DbUtils.getRowIntegerDataById(NOTE_TYPES_TABLE_NAME, noteType,
                new String[]{NOTE_TYPES_CATEGORY_COLUMN}).get(0);
        String category = DbUtils.getRowStringDataById(CATEGORIES_TABLE_NAME, categoryId,
                new String[]{CATEGORIES_CATEGORY_COLUMN}).get(0);

        // Get note default name for such type data
        String defaultName = DbUtils.getRowStringDataById(NOTE_TYPES_TABLE_NAME, noteType,
                new String[]{NOTE_TYPES_DEFAULT_NAME_COLUMN}).get(0);

        // Construct a file path and make a file
        String filePath = rootContentLocation + "/Introvert/" + category + "/" + defaultName;
        Log.i(TAG, "about to create folder: " + filePath);
        File file = makeFileForPath(filePath);

        // Create folders if don't exist
        return mkDirs(file);
    }


}
