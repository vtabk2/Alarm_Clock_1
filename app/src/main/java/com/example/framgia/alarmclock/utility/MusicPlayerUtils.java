package com.example.framgia.alarmclock.utility;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.RawRes;
import android.widget.Toast;

import com.example.framgia.alarmclock.R;

import java.io.IOException;

/**
 * Created by framgia on 25/07/2016.
 */
public class MusicPlayerUtils {
    private static MediaPlayer sMediaPlayer = new MediaPlayer();

    public static void playSong(Activity activity, String path) {
        try {
            sMediaPlayer.reset();
            sMediaPlayer.setDataSource(path);
            sMediaPlayer.prepare();
            sMediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(activity, R.string.error_play_song, Toast.LENGTH_SHORT).show();
        }
    }

    public static void playSound(Activity activity, @RawRes int resId) {
        sMediaPlayer.reset();
        sMediaPlayer.setLooping(true);
        sMediaPlayer = MediaPlayer.create(activity, resId);
        sMediaPlayer.start();
    }

    public static void stopPlaying() {
        sMediaPlayer.stop();
    }
}
