package com.example.framgia.alarmclock.data.listener;

import android.support.annotation.RawRes;

/**
 * Created by framgia on 22/07/2016.
 */
public interface OnSelectMusicListener {
    void onSelected(String musicName, String musicPath);
    String getMusicName();
    void onSelected(String musicName, @RawRes int resId);
}
