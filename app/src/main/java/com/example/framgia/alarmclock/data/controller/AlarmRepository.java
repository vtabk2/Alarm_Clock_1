package com.example.framgia.alarmclock.data.controller;

import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.model.Alarm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by framgia on 12/07/2016.
 */
public class AlarmRepository {
    private Realm mRealm;

    public AlarmRepository(Realm realm) {
        mRealm = realm;
    }

    public void addAlarm(final Alarm alarm) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealm(alarm);
            }
        });
    }

    public RealmResults<Alarm> getAllAlarms() {
        return mRealm.where(Alarm.class).findAll();
    }

    public Alarm getAlarmById(int id) {
        return mRealm.where(Alarm.class).equalTo(Constants.ID_FIELD, id).findFirst();
    }

    public void updateAlarm(final Alarm alarm) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealmOrUpdate(alarm);
            }
        });
    }

    public void deleteAlarm(final Alarm alarm) {
        deleteAlarmById(alarm.getId());
    }

    public void deleteAlarmById(final int id) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmObject result = realm.where(Alarm.class).equalTo(Constants.ID_FIELD, id)
                    .findFirst();
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAll() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.delete(Alarm.class);
            }
        });
    }

    public int getNextId() {
        return getSize() == 0 ? 1 : mRealm.where(Alarm.class).findAll().last().getId() + 1;
    }

    public int getSize() {
        return mRealm.where(Alarm.class).findAll().size();
    }
}
