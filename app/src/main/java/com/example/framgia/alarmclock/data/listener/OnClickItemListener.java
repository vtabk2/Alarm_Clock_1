package com.example.framgia.alarmclock.data.listener;

import com.example.framgia.alarmclock.ui.adapter.AlarmRecyclerViewAdapter;

/**
 * Created by framgia on 14/07/2016.
 */
public interface OnClickItemListener {
    void onClickItem(AlarmRecyclerViewAdapter.AlarmViewHolder holder, int position);
}
