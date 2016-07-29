package com.example.framgia.alarmclock.data.model;

/**
 * Created by framgia on 28/07/2016.
 */
public class Music {
    private String mPath;
    private String mName;
    private boolean mIsChecked;

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getName() {
        return mName;
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
}
