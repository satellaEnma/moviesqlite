package com.kagu.submitthree.MoviePackage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kagu.submitthree.DetailActivity.DetailTmdbTvActivity;
import com.kagu.submitthree.Interface.GetTmdbCallback;
import com.kagu.submitthree.Model.Tmdb;
import com.kagu.submitthree.Model.ViewModelTmdb;
import com.kagu.submitthree.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private RecyclerView rvTmdb;
    private MovieAdapter adapterTmdb;
    private ProgressBar progressBar;
    private LibraryMovie libraryMovie;
    private ViewModelTmdb viewModelTmdb;
    private Context context;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelTmdb = new ViewModelTmdb();
        viewModelTmdb = ViewModelProviders.of(getActivity()).get(ViewModelTmdb.class);
        viewModelTmdb.getTmdb(getActivity()).observe(getActivity(),getTmdbMovie);
    }

    private Observer<List<Tmdb>> getTmdbMovie = new Observer<List<Tmdb>>() {
        @Override
        public void onChanged(List<Tmdb> tmdbs) {
            if (tmdbs != null){
                adapterTmdb = new MovieAdapter(tmdbs, getContext());
                rvTmdb.setAdapter(adapterTmdb);
                LoadBar(false);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views = inflater.inflate(R.layout.fragment_movies, container, false);
        libraryMovie = LibraryMovie.getInstance();
        progressBar = views.findViewById(R.id.progress_barMovie);

        rvTmdb = views.findViewById(R.id.rvTmdbMovie);
        rvTmdb.setHasFixedSize(true);
        rvTmdb.setLayoutManager(new LinearLayoutManager(getContext()));

        return views;

    }

    private void LoadBar(boolean load) {
        if (load) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public MoviesFragment() {
        // Required empty public constructor
    }
}
