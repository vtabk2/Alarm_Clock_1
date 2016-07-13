package com.example.framgia.alarmclock.data.model;

import java.io.File;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by framgia on 12/07/2016.
 */
public class Song extends RealmObject {
    @PrimaryKey
    private int mId;
    private String mPath;
    private String mName;

    public Song(int id, String path, String name) {
        mId = id;
        mPath = path;
        mName = name;
    }

    public int getId() {
        return mId;
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
}
