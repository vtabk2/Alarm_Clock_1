package com.example.framgia.alarmclock.data.listener;

import com.example.framgia.alarmclock.ui.adapter.AlarmRecyclerViewAdapter;

/**
 * Created by framgia on 15/07/2016.
 */
public interface OnLongClickItemListener {
    void onLongClickItem(AlarmRecyclerViewAdapter.AlarmViewHolder holder, int position);
}
