package com.example.framgia.alarmclock.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.listener.OnClickCheckedChangeItemListener;
import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;

import java.util.List;

/**
 * Created by framgia on 21/07/2016.
 */
public class MusicRecyclerViewAdapter
    extends RecyclerView.Adapter<MusicRecyclerViewAdapter.MusicViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<String> mMusicList;
    private OnSelectMusicListener mOnSelectMusicListener;
    private OnClickCheckedChangeItemListener mOnClickCheckedChangeItemListener;

    public MusicRecyclerViewAdapter(Context context, List<String> musicList,
                                    OnSelectMusicListener onSelectMusicListener,
                                    OnClickCheckedChangeItemListener onClickCheckedChangeItemListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mMusicList = musicList;
        mOnSelectMusicListener = onSelectMusicListener;
        mOnClickCheckedChangeItemListener = onClickCheckedChangeItemListener;
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
        holder.mRadioButtonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickCheckedChangeItemListener.onClickCheckedChangeItem(view, holder, position);
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
