package com.kagu.submitthree.TvPackage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kagu.submitthree.DetailActivity.DetailTmdbTvActivity;
import com.kagu.submitthree.Interface.GetTmdbCallback;
import com.kagu.submitthree.Model.Tmdb;
import com.kagu.submitthree.Model.ViewModelTmdb;
import com.kagu.submitthree.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    private RecyclerView rvTv;
    private TvAdapter tvAdapter;
    private ProgressBar progressBarTv;
    private LibraryTv libraryTv;
    private ViewModelTmdb viewModelTmdb;
    private Context context;
    private List<Tmdb> tmdbs;
    public static final String ID_TV_SAVE = "tv";

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelTmdb = new ViewModelTmdb();
        viewModelTmdb = ViewModelProviders.of(getActivity()).get(ViewModelTmdb.class);
        viewModelTmdb.getTmdbTv(getActivity()).observe(getActivity(),geTmdbTv);
    }

    private Observer<List<Tmdb>> geTmdbTv = new Observer<List<Tmdb>>() {
        @Override
        public void onChanged(List<Tmdb> tmdbs) {
            if (tmdbs != null){
                tvAdapter = new TvAdapter(tmdbs, getContext());
                rvTv.setAdapter(tvAdapter);
                LoadBar(false);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View views = inflater.inflate(R.layout.fragment_tv, container, false);
        libraryTv = LibraryTv.getInstance();
        progressBarTv = views.findViewById(R.id.progress_barTv);

        rvTv = views.findViewById(R.id.rvTmdbTv);
        rvTv.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTv.setHasFixedSize(true);

        return views;
    }

    private void LoadBar(boolean loadTv) {
        if (loadTv) {
            progressBarTv.setVisibility(View.VISIBLE);
        } else {
            progressBarTv.setVisibility(View.GONE);
        }
    }

}
