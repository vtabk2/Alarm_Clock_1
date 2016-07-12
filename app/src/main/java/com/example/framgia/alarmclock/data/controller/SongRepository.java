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
    private Realm mRealm;

    public SongRepository(Realm realm) {
        mRealm = realm;
    }

    public void addSong(final Song song) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealm(song);
            }
        });
    }

    public RealmResults<Song> getAllSongs() {
        return mRealm.where(Song.class).findAll();
    }

    public Song getSongById(int id) {
        return mRealm.where(Song.class).equalTo(Constants.ID_FIELD, id).findFirst();
    }

    public void updateSong(final Song song) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealmOrUpdate(song);
            }
        });
    }

    public void deleteSong(final Song song) {
        deleteSongById(song.getId());
    }

    public void deleteSongById(final int id) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmObject result =
                    mRealm.where(Song.class).equalTo(Constants.ID_FIELD, id).findFirst();
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAll() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.delete(Song.class);
            }
        });
    }

    public int getNextId() {
        return getSize() == 0 ? 1 : mRealm.where(Song.class).findAll().last().getId() + 1;
    }

    public int getSize() {
        return mRealm.where(Song.class).findAll().size();
    }
}
