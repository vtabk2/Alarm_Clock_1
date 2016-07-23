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
    public static final String MOUNTAIN = "Mountain";
    public static final String OLD_ALARM_CLOCK = "Old Alarm CLock";
    public static final String DIGITAL = "Digital";
    public static final String BELLS = "Bells";
    public static final String GET_FUNKY = "Get Funky";
    public static final String GOOD_MORNING = "Good Morning";
    public static final String MELLOW = "Mellow";
    public static final String ELECTRO = "Electro";
    public static final String FUTURE = "Future";
    private RadioButton mRadioButtonMountain, mRadioButtonOldAlarmClock, mRadioButtonDigital,
        mRadioButtonBells, mRadioButtonGetFunky, mRadioButtonGoodMorning, mRadioButtonMellow,
        mRadioButtonElectro, mRadioButtonFuture;
    private OnSelectMusicListener mOnSelectMusicListener;

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
        mRadioButtonMountain = (RadioButton) view.findViewById(R.id.radio_button_mountain);
        mRadioButtonOldAlarmClock =
            (RadioButton) view.findViewById(R.id.radio_button_old_alarm_clock);
        mRadioButtonDigital = (RadioButton) view.findViewById(R.id.radio_button_digital);
        mRadioButtonBells = (RadioButton) view.findViewById(R.id.radio_button_bells);
        mRadioButtonGetFunky = (RadioButton) view.findViewById(R.id.radio_button_get_funky);
        mRadioButtonGoodMorning = (RadioButton) view.findViewById(R.id.radio_button_good_morning);
        mRadioButtonMellow = (RadioButton) view.findViewById(R.id.radio_button_mellow);
        mRadioButtonElectro = (RadioButton) view.findViewById(R.id.radio_button_electro);
        mRadioButtonFuture = (RadioButton) view.findViewById(R.id.radio_button_future);
        ((RadioGroup) view.findViewById(R.id.radio_group_sound)).setOnCheckedChangeListener(this);
    }

    private void setDataToViews() {
        switch (mOnSelectMusicListener.getMusicName()) {
            case MOUNTAIN:
                mRadioButtonMountain.setChecked(true);
                break;
            case OLD_ALARM_CLOCK:
                mRadioButtonOldAlarmClock.setChecked(true);
                break;
            case DIGITAL:
                mRadioButtonDigital.setChecked(true);
                break;
            case BELLS:
                mRadioButtonBells.setChecked(true);
                break;
            case GET_FUNKY:
                mRadioButtonGetFunky.setChecked(true);
                break;
            case GOOD_MORNING:
                mRadioButtonGoodMorning.setChecked(true);
                break;
            case MELLOW:
                mRadioButtonMellow.setChecked(true);
                break;
            case ELECTRO:
                mRadioButtonElectro.setChecked(true);
                break;
            case FUTURE:
                mRadioButtonFuture.setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_button_mountain:
                mOnSelectMusicListener.onSelected(MOUNTAIN, R.raw.mountain);
                break;
            case R.id.radio_button_old_alarm_clock:
                mOnSelectMusicListener.onSelected(OLD_ALARM_CLOCK, R.raw.old_alarm_clock);
                break;
            case R.id.radio_button_digital:
                mOnSelectMusicListener.onSelected(DIGITAL, R.raw.digital);
                break;
            case R.id.radio_button_bells:
                mOnSelectMusicListener.onSelected(BELLS, R.raw.bells);
                break;
            case R.id.radio_button_get_funky:
                mOnSelectMusicListener.onSelected(GET_FUNKY, R.raw.get_funky);
                break;
            case R.id.radio_button_good_morning:
                mOnSelectMusicListener.onSelected(GOOD_MORNING, R.raw.good_morning);
                break;
            case R.id.radio_button_mellow:
                mOnSelectMusicListener.onSelected(MELLOW, R.raw.mellow);
                break;
            case R.id.radio_button_electro:
                mOnSelectMusicListener.onSelected(ELECTRO, R.raw.electro);
                break;
            case R.id.radio_button_future:
                mOnSelectMusicListener.onSelected(FUTURE, R.raw.future);
                break;
        }
    }
}
