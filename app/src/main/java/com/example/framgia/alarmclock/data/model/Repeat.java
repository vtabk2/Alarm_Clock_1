package com.example.framgia.alarmclock.data.model;

import com.example.framgia.alarmclock.data.Constants;

import io.realm.RealmObject;

/**
 * Created by framgia on 12/07/2016.
 */
public class Repeat extends RealmObject {
    private boolean mIsRepeatMonday;
    private boolean mIsRepeatTuesday;
    private boolean mIsRepeatWednesday;
    private boolean mIsRepeatThursday;
    private boolean mIsRepeatFriday;
    private boolean mIsRepeatSaturday;
    private boolean mIsRepeatSunday;
    private boolean mIsRepeatEveryday;
    private String mRepeatDay;

    public Repeat() {
    }

    public Repeat(boolean isRepeatMonday, boolean isRepeatTuesday, boolean isRepeatWednesday,
                  boolean isRepeatThursday, boolean isRepeatFriday, boolean isRepeatSaturday,
                  boolean isRepeatSunday, boolean isRepeatEveryday) {
        mIsRepeatMonday = isRepeatMonday;
        mIsRepeatTuesday = isRepeatTuesday;
        mIsRepeatWednesday = isRepeatWednesday;
        mIsRepeatThursday = isRepeatThursday;
        mIsRepeatFriday = isRepeatFriday;
        mIsRepeatSaturday = isRepeatSaturday;
        mIsRepeatSunday = isRepeatSunday;
        mIsRepeatEveryday = isRepeatEveryday;
        initRepeatDay();
    }

    public boolean isRepeatMonday() {
        return mIsRepeatMonday;
    }

    public boolean isRepeatTuesday() {
        return mIsRepeatTuesday;
    }

    public boolean isRepeatWednesday() {
        return mIsRepeatWednesday;
    }

    public boolean isRepeatThursday() {
        return mIsRepeatThursday;
    }

    public boolean isRepeatFriday() {
        return mIsRepeatFriday;
    }

    public boolean isRepeatSaturday() {
        return mIsRepeatSaturday;
    }

    public boolean isRepeatSunday() {
        return mIsRepeatSunday;
    }

    public boolean isRepeatEveryday() {
        return mIsRepeatEveryday;
    }

    public String getRepeatDay() {
        return mRepeatDay;
    }

    private void initRepeatDay() {
        mRepeatDay = mIsRepeatEveryday ? Constants.EVERYDAY : getAlarmDay();
    }

    private String getAlarmDay() {
        StringBuilder repeatDay = new StringBuilder("");
        if (mIsRepeatMonday)
            repeatDay.append(Constants.NAMES_OF_DAYS[2]);
        if (mIsRepeatTuesday)
            repeatDay.append(checkRepeatDay(repeatDay) + Constants.NAMES_OF_DAYS[3]);
        if (mIsRepeatWednesday)
            repeatDay.append(checkRepeatDay(repeatDay) + Constants.NAMES_OF_DAYS[4]);
        if (mIsRepeatThursday)
            repeatDay.append(checkRepeatDay(repeatDay) + Constants.NAMES_OF_DAYS[5]);
        if (mIsRepeatFriday)
            repeatDay.append(checkRepeatDay(repeatDay) + Constants.NAMES_OF_DAYS[6]);
        if (mIsRepeatSaturday)
            repeatDay.append(checkRepeatDay(repeatDay) + Constants.NAMES_OF_DAYS[7]);
        if (mIsRepeatSunday)
            repeatDay.append(checkRepeatDay(repeatDay) + Constants.NAMES_OF_DAYS[1]);
        return repeatDay.toString();
    }

    private String checkRepeatDay(StringBuilder repeatDay) {
        return repeatDay.toString().equals("") ? "" : Constants.NAMES_OF_DAYS_SEPERATOR;
    }
}
