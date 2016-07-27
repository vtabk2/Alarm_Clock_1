package com.example.framgia.alarmclock.utility;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by framgia on 27/07/2016.
 */
public class MusicUtils {
    public static List<String> sMusicslist = new ArrayList<>();
    public static List<String> sPathsList = new ArrayList<>();

    public static void loadMusicsList(Activity activity) {
        Cursor cursor = activity.getContentResolver()
            .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA},
                null, null, "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                sMusicslist.add(cursor
                    .getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                sPathsList.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio
                    .Media.DATA)));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
