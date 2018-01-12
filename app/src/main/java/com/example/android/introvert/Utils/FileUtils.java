package com.example.android.introvert.Utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by takeoff on 009 09 Jan 18.
 */

public class FileUtils {
    private static final String TAG = "INTROWERT_UTILS:";

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
    public static String INTERNAL_APP_STORAGE;
    // /storage/sdcard0/Android/data/com.example.android.introvert/files
    public static String EXTERNAL_APP_STORAGE;
    // /storage/sdcard0
    public static final String EXTERNAL_STORAGE = getAbsolutePathOfFile(getExternalStorageRootDir());
    public static final String SD_STORAGE = "/storage/sdcard1";


    /* Checks if external storage is mounted */
    public static boolean externalStorageIsReady() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /* Returns root directory of external storage drive */
    private static File getExternalStorageRootDir() {
        if (externalStorageIsReady()) return Environment.getExternalStorageDirectory();
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
}
