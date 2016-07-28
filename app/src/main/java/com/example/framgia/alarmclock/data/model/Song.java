package com.example.framgia.alarmclock.data.model;

import java.io.File;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by framgia on 12/07/2016.
 */
public class Song extends RealmObject {
    private String mPath;
    private String mName;
    private boolean mIsChecked;
    private boolean mIsAlarmMusic;

    public Song() {
    }

    public String getPath() {
        return mPath;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return mPath + File.separator + mName;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    public boolean isAlarmMusic() {
        return mIsAlarmMusic;
    }

    public void setAlarmMusic(boolean alarmMusic) {
        mIsAlarmMusic = alarmMusic;
    }

    public void copyFrom(Song song) {
        mName = song.getName();
        mPath = song.getPath();
        mIsChecked = song.isChecked();
        mIsAlarmMusic = song.isAlarmMusic();
    }
}
