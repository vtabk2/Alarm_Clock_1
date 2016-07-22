package com.example.framgia.alarmclock.ui.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by framgia on 18/07/2016.
 */
public class TimePickerFragment extends DialogFragment implements
    TimePickerDialog.OnTimeSetListener {
    private final static int HOUR_OF_DAY = 0;
    private final static int MINUTE = 1;
    private TextView mTextViewTimePicker;

    public TimePickerFragment setTextViewTimePicker(TextView textViewTimePicker) {
        this.mTextViewTimePicker = textViewTimePicker;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String tmp = mTextViewTimePicker.getText().toString();
        String[] arr = tmp.split(":");
        int hourOfDay = Integer.parseInt(arr[HOUR_OF_DAY]);
        int minute = Integer.parseInt(arr[MINUTE]);
        return new TimePickerDialog(getContext(), this, hourOfDay, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mTextViewTimePicker.setText(getFormatTime(hourOfDay, minute));
    }

    private String getFormatTime(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }
}
