package com.example.framgia.alarmclock.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;

/**
 * Created by framgia on 21/07/2016.
 */
public class SoundFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private final String OLD_ALARM_CLOCK = "Old Alarm CLock";
    private final String DIGITAL = "Digital";
    private final String BELLS = "Bells";
    private OnSelectMusicListener mOnSelectMusicListener;
    private RadioButton mRadioButtonOldAlarmClock, mRadioButtonDigital, mRadioButtonBells;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound, viewGroup, false);
        initViews(view);
        setDataToViews();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnSelectMusicListener = (OnSelectMusicListener) context;
    }

    private void initViews(View view) {
        mRadioButtonOldAlarmClock = (RadioButton) view.findViewById(R.id
            .radio_button_old_alarm_clock);
        mRadioButtonDigital = (RadioButton) view.findViewById(R.id.radio_button_digital);
        mRadioButtonBells = (RadioButton) view.findViewById(R.id.radio_button_bells);
        ((RadioGroup) view.findViewById(R.id.radio_group_sound)).setOnCheckedChangeListener(this);
    }

    private void setDataToViews() {
        switch (mOnSelectMusicListener.getMusicName()) {
            case OLD_ALARM_CLOCK:
                mRadioButtonOldAlarmClock.setChecked(true);
                break;
            case DIGITAL:
                mRadioButtonDigital.setChecked(true);
                break;
            case BELLS:
                mRadioButtonBells.setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_old_alarm_clock:
                mOnSelectMusicListener.onSelected(OLD_ALARM_CLOCK, OLD_ALARM_CLOCK);
                break;
            case R.id.radio_button_digital:
                mOnSelectMusicListener.onSelected(DIGITAL, DIGITAL);
                break;
            case R.id.radio_button_bells:
                mOnSelectMusicListener.onSelected(BELLS, BELLS);
                break;
        }
    }
}
