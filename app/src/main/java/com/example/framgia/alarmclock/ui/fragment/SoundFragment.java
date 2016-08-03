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
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.listener.OnFragmentIsVisible;
import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;

/**
 * Created by framgia on 21/07/2016.
 */
public class SoundFragment extends Fragment implements RadioGroup.OnCheckedChangeListener,
    OnFragmentIsVisible {
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
            case Constants.MOUNTAIN:
                mRadioButtonMountain.setChecked(true);
                break;
            case Constants.OLD_ALARM_CLOCK:
                mRadioButtonOldAlarmClock.setChecked(true);
                break;
            case Constants.DIGITAL:
                mRadioButtonDigital.setChecked(true);
                break;
            case Constants.BELLS:
                mRadioButtonBells.setChecked(true);
                break;
            case Constants.GET_FUNKY:
                mRadioButtonGetFunky.setChecked(true);
                break;
            case Constants.GOOD_MORNING:
                mRadioButtonGoodMorning.setChecked(true);
                break;
            case Constants.MELLOW:
                mRadioButtonMellow.setChecked(true);
                break;
            case Constants.ELECTRO:
                mRadioButtonElectro.setChecked(true);
                break;
            case Constants.FUTURE:
                mRadioButtonFuture.setChecked(true);
                break;
            default:
                mRadioButtonMountain.setChecked(false);
                mRadioButtonOldAlarmClock.setChecked(false);
                mRadioButtonDigital.setChecked(false);
                mRadioButtonBells.setChecked(false);
                mRadioButtonGetFunky.setChecked(false);
                mRadioButtonGoodMorning.setChecked(false);
                mRadioButtonMellow.setChecked(false);
                mRadioButtonElectro.setChecked(false);
                mRadioButtonFuture.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_mountain:
                mOnSelectMusicListener
                    .onSelected(Constants.MOUNTAIN, String.valueOf(R.raw.mountain));
                break;
            case R.id.radio_button_old_alarm_clock:
                mOnSelectMusicListener.onSelected(Constants.OLD_ALARM_CLOCK,
                    String.valueOf(R.raw.old_alarm_clock));
                break;
            case R.id.radio_button_digital:
                mOnSelectMusicListener.onSelected(Constants.DIGITAL, String.valueOf(R.raw.digital));
                break;
            case R.id.radio_button_bells:
                mOnSelectMusicListener.onSelected(Constants.BELLS, String.valueOf(R.raw.bells));
                break;
            case R.id.radio_button_get_funky:
                mOnSelectMusicListener
                    .onSelected(Constants.GET_FUNKY, String.valueOf(R.raw.get_funky));
                break;
            case R.id.radio_button_good_morning:
                mOnSelectMusicListener
                    .onSelected(Constants.GOOD_MORNING, String.valueOf(R.raw.good_morning));
                break;
            case R.id.radio_button_mellow:
                mOnSelectMusicListener.onSelected(Constants.MELLOW, String.valueOf(R.raw.mellow));
                break;
            case R.id.radio_button_electro:
                mOnSelectMusicListener.onSelected(Constants.ELECTRO, String.valueOf(R.raw.electro));
                break;
            case R.id.radio_button_future:
                mOnSelectMusicListener.onSelected(Constants.FUTURE, String.valueOf(R.raw.future));
                break;
        }
    }

    @Override
    public void fragmentIsVisible() {
        setDataToViews();
    }
}
