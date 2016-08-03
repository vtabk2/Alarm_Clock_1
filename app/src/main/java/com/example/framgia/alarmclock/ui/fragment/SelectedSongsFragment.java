package com.example.framgia.alarmclock.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.controller.SongRepository;
import com.example.framgia.alarmclock.data.listener.OnClickItemListener;
import com.example.framgia.alarmclock.data.listener.OnFragmentIsVisible;
import com.example.framgia.alarmclock.data.model.Song;
import com.example.framgia.alarmclock.ui.adapter.SelectedSongsRecyclerViewAdapter;

import java.util.List;

/**
 * Created by framgia on 29/07/2016.
 */
public class SelectedSongsFragment extends Fragment implements OnClickItemListener,
    OnFragmentIsVisible {
    private SelectedSongsRecyclerViewAdapter mSelectedSongsRecyclerViewAdapter;
    private List<Song> mSelectedSongs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, viewGroup, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mSelectedSongs = SongRepository.getAllSongs();
        RecyclerView recyclerViewSelectedSongs =
            (RecyclerView) view.findViewById(R.id.recycler_view_list_musics);
        mSelectedSongsRecyclerViewAdapter = new SelectedSongsRecyclerViewAdapter(getActivity(),
            mSelectedSongs, this);
        recyclerViewSelectedSongs.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSelectedSongs.setAdapter(mSelectedSongsRecyclerViewAdapter);
    }

    @Override
    public void onClickItem(View view, int position) {
        switch (view.getId()) {
            case R.id.button_delete_selected_song:
                SongRepository.deleteSong(mSelectedSongs.get(position));
                mSelectedSongsRecyclerViewAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void fragmentIsVisible() {
        mSelectedSongsRecyclerViewAdapter.notifyDataSetChanged();
    }
}
