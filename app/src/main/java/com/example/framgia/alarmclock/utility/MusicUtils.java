package com.example.framgia.alarmclock.utility;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.framgia.alarmclock.data.model.Music;

import java.util.List;

/**
 * Created by framgia on 27/07/2016.
 */
public class MusicUtils {
    public static void loadMusicsList(Context context, List<Music> musicList) {
        Cursor cursor = context.getContentResolver()
            .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA},
                null, null, "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Music music = new Music();
                music.setName(cursor
                    .getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                music.setPath(
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                musicList.add(music);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
