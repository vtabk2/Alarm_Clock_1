package com.example.framgia.alarmclock.data.controller;

import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.model.Song;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by framgia on 12/07/2016.
 */
public class SongRepository {
    private static Realm mRealm = Realm.getDefaultInstance();

    public static RealmResults<Song> getAllSongs() {
        return mRealm.where(Song.class).equalTo(Constants.IS_ALARM_MUSIC_FIELD, false).findAll();
    }

    public static Song getSongByPath(String path) {
        return mRealm.where(Song.class).equalTo(Constants.IS_ALARM_MUSIC_FIELD, false).equalTo
            (Constants.PATH_FIELD, path).findFirst();
    }

    public static void updateSong(final Song song) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealmOrUpdate(song);
            }
        });
    }

    public static void deleteSong(final Song song) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                song.deleteFromRealm();
            }
        });
    }

    public static void deleteSongById(final int id) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmObject result =
                    mRealm.where(Song.class).equalTo(Constants.ID_FIELD, id).findFirst();
                result.deleteFromRealm();
            }
        });
    }

    public static void deleteAll() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.delete(Song.class);
            }
        });
    }

    public static int getSize() {
        return mRealm.where(Song.class).equalTo(Constants.IS_ALARM_MUSIC_FIELD, false).findAll()
            .size();
    }
}
