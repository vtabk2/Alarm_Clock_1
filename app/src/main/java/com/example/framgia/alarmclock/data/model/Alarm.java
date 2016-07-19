package com.example.framgia.alarmclock.data.model;

import com.example.framgia.alarmclock.data.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Repeat mRepeat;
    private boolean mIsChecked;

    public Alarm() {
    }

    public Alarm(int id) {
        // TODO: 13/07/2016 default alarm
        mId = id;
    }

    public Alarm(int id, long time, String sound, int volume, boolean isVibrated, boolean isFadeIn,
                 int snoozeTime, String note, boolean isEnabled, Repeat repeat) {
        mId = id;
        mTime = time;
        mSound = sound;
        mVolume = volume;
        mIsVibrated = isVibrated;
        mIsFadeIn = isFadeIn;
        mSnoozeTime = snoozeTime;
        mNote = note;
        mIsEnabled = isEnabled;
        mRepeat = repeat;
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

    public Repeat getRepeat() {
        return mRepeat;
    }

    public String getFormattedTime() {
        return new SimpleDateFormat(Constants.ALARM_TIME_FORMAT).format(new Date(mTime));
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }

    public void setVibrated(boolean vibrated) {
        mIsVibrated = vibrated;
    }

    public void setFadeIn(boolean fadeIn) {
        mIsFadeIn = fadeIn;
    }

    public void setRepeat(Repeat repeat) {
        mRepeat = repeat;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public void setSound(String sound) {
        mSound = sound;
    }

    public void setVolume(int volume) {
        mVolume = volume;
    }

    public void setSnoozeTime(int snoozeTime) {
        mSnoozeTime = snoozeTime;
    }

    public void setNote(String note) {
        mNote = note;
    }
}
