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

    public Song() {
    }

    public Song(int id) {
        // TODO: 13/07/2016 default song
    }

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

    public void setId(int id) {
        mId = id;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public void setName(String name) {
        mName = name;
    }
}
