package com.example.framgia.alarmclock.utility;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import com.example.framgia.alarmclock.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by framgia on 28/07/2016.
 */
public class MusicPlayerUtils {
    private static final String URI_RESOURCE = "android.resource://";
    private static MediaPlayer mMediaPlayer;

    public static void playMusic(Context context, String path) {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setLooping(true);
            if (isSound(path))
                mMediaPlayer.setDataSource(context,
                    Uri.parse(URI_RESOURCE + context.getPackageName() + File.separator + path));
            else mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            Toast.makeText(context, R.string.error_play_song, Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isSound(String path) {
        int idPath;
        try {
            idPath = Integer.parseInt(path);
        } catch (NumberFormatException ex) {
            return false;
        }
        switch (idPath) {
            case R.raw.mountain:
            case R.raw.old_alarm_clock:
            case R.raw.digital:
            case R.raw.bells:
            case R.raw.get_funky:
            case R.raw.good_morning:
            case R.raw.mellow:
            case R.raw.electro:
            case R.raw.future:
            case R.raw.noise:
                return true;
            default:
                return false;
        }
    }

    public static void stopMusic() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) mMediaPlayer.stop();
    }
}
