package com.example.framgia.alarmclock.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.listener.OnClickItemListener;
import com.example.framgia.alarmclock.data.model.Song;

import java.util.List;

/**
 * Created by framgia on 29/07/2016.
 */
public class SelectedSongsRecyclerViewAdapter
    extends RecyclerView.Adapter<SelectedSongsRecyclerViewAdapter.SelectedSongViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Song> mSelectedSongs;
    private OnClickItemListener mOnClickItemListener;

    public SelectedSongsRecyclerViewAdapter(Context context, List<Song> selectedSongs,
                                            OnClickItemListener onClickItemListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mSelectedSongs = selectedSongs;
        mOnClickItemListener = onClickItemListener;
    }

    @Override
    public SelectedSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedSongViewHolder(mLayoutInflater.inflate(R.layout.item_selected_songs,
            parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedSongViewHolder holder, final int position) {
        holder.mTextViewSelectedSongName.setText(mSelectedSongs.get(position).getName());
        holder.mButtonDeletedSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickItemListener.onClickItem(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSelectedSongs == null ? 0 : mSelectedSongs.size();
    }

    public class SelectedSongViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewSelectedSongName;
        private Button mButtonDeletedSong;

        public SelectedSongViewHolder(View itemView) {
            super(itemView);
            mTextViewSelectedSongName =
                (TextView) itemView.findViewById(R.id.text_view_selected_song_name);
            mButtonDeletedSong = (Button) itemView.findViewById(R.id.button_delete_selected_song);
        }
    }
}
