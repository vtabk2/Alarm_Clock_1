package com.example.framgia.alarmclock.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.ui.activity.RepeatActivity;

/**
 * Created by framgia on 14/07/2016.
 */
public class PageFragment extends Fragment implements View.OnClickListener {
    private final static int TYPE_FUNCTIONS = 1;
    private final static int TYPE_CLOCKS = 2;
    private final static String PAGE = "page";
    private int mPage;
    private SharedPreferences mSharedPreferences;
    private LinearLayout mLinearLayoutDigitalWhite;
    private LinearLayout mLinearLayoutDigitalBlue;
    private LinearLayout mLinearLayoutDigitalGreen;
    private LinearLayout mLinearLayoutDigitalRed;
    private LinearLayout mLinearLayoutDigitalYellow;
    private LinearLayout mLinearLayoutAnalog;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = null;
        switch (mPage) {
            case TYPE_FUNCTIONS:
                view = inflater.inflate(R.layout.fragment_settings_functions, viewGroup, false);
                initViewFunctions(view);
                break;
            case TYPE_CLOCKS:
                view = inflater.inflate(R.layout.fragment_settings_clocks, viewGroup, false);
                mSharedPreferences = inflater.getContext().
                    getSharedPreferences(Constants.SHARE_PREFERENCES, Context.MODE_PRIVATE);
                initViewClocks(view);
                break;
        }
        return view;
    }

    private void initViewFunctions(View view) {
        view.findViewById(R.id.layout_alarm).setOnClickListener(this);
        view.findViewById(R.id.layout_timer).setOnClickListener(this);
        view.findViewById(R.id.layout_weather).setOnClickListener(this);
        view.findViewById(R.id.layout_display).setOnClickListener(this);
        view.findViewById(R.id.layout_advanced).setOnClickListener(this);
    }

    private void initViewClocks(View view) {
        int type = mSharedPreferences.getInt(Constants.TYPE_CLOCKS, Constants
            .TYPE_CLOCKS_WHITE);
        mLinearLayoutDigitalWhite = (LinearLayout) view.findViewById(R.id.layout_clock_white);
        mLinearLayoutDigitalBlue = (LinearLayout) view.findViewById(R.id.layout_clock_blue);
        mLinearLayoutDigitalGreen = (LinearLayout) view.findViewById(R.id.layout_clock_green);
        mLinearLayoutDigitalRed = (LinearLayout) view.findViewById(R.id.layout_clock_red);
        mLinearLayoutDigitalYellow = (LinearLayout) view.findViewById(R.id.layout_clock_yellow);
        mLinearLayoutAnalog = (LinearLayout) view.findViewById(R.id.layout_clock_analog);
        mLinearLayoutDigitalWhite.setOnClickListener(this);
        mLinearLayoutDigitalBlue.setOnClickListener(this);
        mLinearLayoutDigitalGreen.setOnClickListener(this);
        mLinearLayoutDigitalRed.setOnClickListener(this);
        mLinearLayoutDigitalYellow.setOnClickListener(this);
        mLinearLayoutAnalog.setOnClickListener(this);
        onChangeTypeClock(type, false);
    }

    private int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    private void onChangeTypeClock(int type, boolean change) {
        mLinearLayoutDigitalWhite.setBackgroundColor(
            getColor(type == Constants.TYPE_CLOCKS_WHITE ? R.color.indigo : R.color.colorBlack));
        mLinearLayoutDigitalBlue.setBackgroundColor(
            getColor(type == Constants.TYPE_CLOCKS_BLUE ? R.color.indigo : R.color.colorBlack));
        mLinearLayoutDigitalGreen.setBackgroundColor(
            getColor(type == Constants.TYPE_CLOCKS_GREEN ? R.color.indigo : R.color.colorBlack));
        mLinearLayoutDigitalRed.setBackgroundColor(
            getColor(type == Constants.TYPE_CLOCKS_RED ? R.color.indigo : R.color.colorBlack));
        mLinearLayoutDigitalYellow.setBackgroundColor(
            getColor(type == Constants.TYPE_CLOCKS_YELLOW ? R.color.indigo : R.color.colorBlack));
        if (change) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(Constants.TYPE_CLOCKS, type);
            editor.commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_clock_white:
                onChangeTypeClock(Constants.TYPE_CLOCKS_WHITE, true);
                break;
            case R.id.layout_clock_blue:
                onChangeTypeClock(Constants.TYPE_CLOCKS_BLUE, true);
                break;
            case R.id.layout_clock_green:
                onChangeTypeClock(Constants.TYPE_CLOCKS_GREEN, true);
                break;
            case R.id.layout_clock_red:
                onChangeTypeClock(Constants.TYPE_CLOCKS_RED, true);
                break;
            case R.id.layout_clock_yellow:
                onChangeTypeClock(Constants.TYPE_CLOCKS_YELLOW, true);
                break;
            case R.id.layout_clock_analog:
                onChangeTypeClock(Constants.TYPE_CLOCKS_ANALOG, true);
                break;
            case R.id.layout_alarm:
                // TODO: 14/07/2016
                openActivity(RepeatActivity.class);
                break;
            case R.id.layout_timer:
                // TODO: 14/07/2016
                break;
            case R.id.layout_weather:
                // TODO: 14/07/2016
                break;
            case R.id.layout_display:
                // TODO: 14/07/2016
                break;
            case R.id.layout_advanced:
                // TODO: 14/07/2016
                break;
        }
    }

    private void openActivity(Class myClass) {
        Intent intent = new Intent(getContext(), myClass);
        startActivity(intent);
    }
}
