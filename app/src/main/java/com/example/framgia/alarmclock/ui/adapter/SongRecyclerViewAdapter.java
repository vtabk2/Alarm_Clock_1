package com.example.framgia.alarmclock.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.listener.OnClickCheckedChangeItemListener;
import com.example.framgia.alarmclock.data.model.Music;

import java.util.List;

/**
 * Created by framgia on 25/07/2016.
 */
public class SongRecyclerViewAdapter
    extends RecyclerView.Adapter<SongRecyclerViewAdapter.SongViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Music> mMusicList;
    private OnClickCheckedChangeItemListener mOnClickCheckedChangeItemListener;

    public SongRecyclerViewAdapter(Context context, List<Music> musicList,
                                   OnClickCheckedChangeItemListener onClickCheckedChangeItemListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mMusicList = musicList;
        mOnClickCheckedChangeItemListener = onClickCheckedChangeItemListener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongViewHolder(mLayoutInflater.inflate(R.layout.item_song, parent, false));
    }

    @Override
    public void onBindViewHolder(final SongViewHolder holder, final int position) {
        Music music = mMusicList.get(position);
        holder.mCheckBoxSelectSong.setText(music.getName());
        holder.mCheckBoxSelectSong.setChecked(music.isChecked());
        holder.mCheckBoxSelectSong.setOnClickListener(new View.OnClickListener() {
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

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCheckBoxSelectSong;

        public SongViewHolder(View itemView) {
            super(itemView);
            mCheckBoxSelectSong = (CheckBox) itemView.findViewById(R.id.checkbox_select_song);
        }
    }
}
