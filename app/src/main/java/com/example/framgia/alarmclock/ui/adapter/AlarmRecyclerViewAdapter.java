package com.example.framgia.alarmclock.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.listener.OnCheckedChangeItemListener;
import com.example.framgia.alarmclock.data.listener.OnClickItemListener;
import com.example.framgia.alarmclock.data.listener.OnLongClickItemListener;
import com.example.framgia.alarmclock.data.model.Alarm;

import java.util.List;

/**
 * Created by framgia on 13/07/2016.
 */
public class AlarmRecyclerViewAdapter
    extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmViewHolder> {
    public static boolean IS_SHOWED_CHECKBOX = false;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Alarm> mAlarmList;
    private OnClickItemListener mOnClickItemListener;
    private OnCheckedChangeItemListener mOnCheckedChangeItemListener;
    private OnLongClickItemListener mOnLongClickItemListener;

    public AlarmRecyclerViewAdapter(Context context, List<Alarm> alarmList, OnClickItemListener
        onClickItemListener, OnCheckedChangeItemListener onCheckedChangeItemListener,
                                    OnLongClickItemListener onLongClickItemListener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mAlarmList = alarmList;
        mOnClickItemListener = onClickItemListener;
        mOnCheckedChangeItemListener = onCheckedChangeItemListener;
        mOnLongClickItemListener = onLongClickItemListener;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlarmViewHolder holder, final int position) {
        Alarm alarm = mAlarmList.get(position);
        holder.mTextViewAlarmTime.setText(alarm.getFormattedTime());
        holder.mTextViewAlarmTime.setTextColor(alarm.isEnabled() ? Color.WHITE : Color.GRAY);
        holder.mTextViewAlarmDay.setText(alarm.getRepeat().getRepeatDay());
        holder.mTextViewAlarmDay.setTextColor(alarm.isEnabled() ? Color.WHITE : Color.GRAY);
        holder.mTextViewAlarmNote.setText(alarm.getNote());
        holder.mCheckBoxSelectAlarm.setVisibility(IS_SHOWED_CHECKBOX ? View.VISIBLE : View.GONE);
        holder.mSwitchEnableAlarm.setClickable(IS_SHOWED_CHECKBOX ? false : true);
        holder.mSwitchEnableAlarm.setChecked(alarm.isEnabled());
        holder.mCheckBoxSelectAlarm.setChecked(alarm.isChecked());
        holder.mRelativeLayoutItemAlarm.setBackgroundColor(getColor(alarm.isChecked() ? R.color
            .indigo : R.color.black));
        holder.mRelativeLayoutItemAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickItemListener.onClickItem(view, holder.getLayoutPosition());
            }
        });
        holder.mRelativeLayoutItemAlarm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnLongClickItemListener.onLongClickItem(view, holder, holder.getLayoutPosition());
                return false;
            }
        });
        holder.mSwitchEnableAlarm.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mOnCheckedChangeItemListener
                        .onCheckedChangeItem(holder, position, compoundButton, b);
                }
            });
        holder.mCheckBoxSelectAlarm.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mOnCheckedChangeItemListener
                        .onCheckedChangeItem(holder, position, compoundButton, b);
                }
            });
    }

    private int getColor(int id) {
        return ContextCompat.getColor(mContext, id);
    }

    @Override
    public int getItemCount() {
        return mAlarmList == null ? 0 : mAlarmList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mRelativeLayoutItemAlarm;
        public TextView mTextViewAlarmTime;
        public TextView mTextViewAlarmDay;
        public TextView mTextViewAlarmNote;
        public Switch mSwitchEnableAlarm;
        public CheckBox mCheckBoxSelectAlarm;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            mRelativeLayoutItemAlarm =
                (RelativeLayout) itemView.findViewById(R.id.relative_layout_item_alarm);
            mTextViewAlarmTime = (TextView) itemView.findViewById(R.id.text_alarm_time);
            mTextViewAlarmDay = (TextView) itemView.findViewById(R.id.text_alarm_day);
            mTextViewAlarmNote = (TextView) itemView.findViewById(R.id.text_alarm_note);
            mSwitchEnableAlarm = (Switch) itemView.findViewById(R.id.switch_enable_alarm);
            mCheckBoxSelectAlarm =
                (CheckBox) itemView.findViewById(R.id.checkbox_select_alarm);
        }
    }
}
