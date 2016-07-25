package com.example.framgia.alarmclock.ui.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.utility.AlarmUtils;

public class AlarmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.ACTION_BOOT_COMPLETED)) {
            AlarmUtils.setupAlarmBoot(context);
        }
    }
}