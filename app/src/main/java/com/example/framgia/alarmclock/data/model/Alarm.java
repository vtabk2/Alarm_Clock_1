package com.example.framgia.alarmclock.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by framgia on 12/07/2016.
 */
public class Alarm extends RealmObject {
    @PrimaryKey
    private int mId;
    private long mTime;
    private String mSound;
    private int mVolume;
    private boolean mIsVibrated;
    private boolean mIsFadeIn;
    private int mSnoozeTime;
    private String mNote;
    private boolean mIsEnabled;
    private Repeat mRepeatDay;

    public Alarm(int id, long time, String sound, int volume, boolean isVibrated, boolean isFadeIn,
                 int snoozeTime, String note, boolean isEnabled,
                 Repeat repeatDay) {
        mId = id;
        mTime = time;
        mSound = sound;
        mVolume = volume;
        mIsVibrated = isVibrated;
        mIsFadeIn = isFadeIn;
        mSnoozeTime = snoozeTime;
        mNote = note;
        mIsEnabled = isEnabled;
        mRepeatDay = repeatDay;
    }

    public int getId() {
        return mId;
    }

    public long getTime() {
        return mTime;
    }

    public String getSound() {
        return mSound;
    }

    public int getVolume() {
        return mVolume;
    }

    public boolean isVibrated() {
        return mIsVibrated;
    }

    public boolean isFadeIn() {
        return mIsFadeIn;
    }

    public int getSnoozeTime() {
        return mSnoozeTime;
    }

    public String getNote() {
        return mNote;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public Repeat getRepeatDay() {
        return mRepeatDay;
    }
}
