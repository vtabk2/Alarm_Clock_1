package com.example.framgia.alarmclock.data.listener;

import android.widget.CompoundButton;

import com.example.framgia.alarmclock.ui.adapter.AlarmRecyclerViewAdapter;

/**
 * Created by framgia on 15/07/2016.
 */
public interface OnCheckedChangeItemListener {
    void onCheckedChangeItem(AlarmRecyclerViewAdapter.AlarmViewHolder holder, int positon,
                             CompoundButton button, boolean isChecked);
}
