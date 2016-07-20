package com.example.framgia.alarmclock.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.listener.OnCheckedChangeItemListener;
import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;

import java.util.List;

/**
 * Created by framgia on 21/07/2016.
 */
public class MusicRecyclerViewAdapter
    extends RecyclerView.Adapter<MusicRecyclerViewAdapter.MusicViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<String> mMusicList;
    private OnCheckedChangeItemListener mOnCheckedChangeItemListener;
    private OnSelectMusicListener mOnSelectMusicListener;

    public MusicRecyclerViewAdapter(Context context, List<String> musicList,
                                    OnCheckedChangeItemListener onCheckedChangeItemListener,
                                    OnSelectMusicListener onSelectMusicListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mMusicList = musicList;
        mOnCheckedChangeItemListener = onCheckedChangeItemListener;
        mOnSelectMusicListener = onSelectMusicListener;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MusicViewHolder holder, final int position) {
        String currentMusic = mMusicList.get(position);
        holder.mRadioButtonMusic.setText(currentMusic);
        holder.mRadioButtonMusic
            .setChecked(mOnSelectMusicListener.getMusicName().equals(currentMusic));
        holder.mRadioButtonMusic.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    mOnCheckedChangeItemListener.onCheckedChangeItem(holder, position,
                        compoundButton, isChecked);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mMusicList == null ? 0 : mMusicList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        private RadioButton mRadioButtonMusic;

        public MusicViewHolder(View itemView) {
            super(itemView);
            mRadioButtonMusic = (RadioButton) itemView.findViewById(R.id.radio_button_music);
        }
    }
}
