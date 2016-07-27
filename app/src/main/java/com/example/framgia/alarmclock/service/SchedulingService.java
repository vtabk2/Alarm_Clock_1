package com.example.framgia.alarmclock.service;

import android.app.IntentService;
import android.content.Intent;

import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.controller.AlarmRepository;
import com.example.framgia.alarmclock.utility.NotificationUtils;

public class SchedulingService extends IntentService {
    public SchedulingService() {
        super(SchedulingService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int id = intent.getIntExtra(Constants.OBJECT_ID, Constants.DEFAULT_INTENT_VALUE);
        if (id > Constants.DEFAULT_INTENT_VALUE)
            NotificationUtils.showNotification(this, AlarmRepository.getAlarmById(id));
    }
}
