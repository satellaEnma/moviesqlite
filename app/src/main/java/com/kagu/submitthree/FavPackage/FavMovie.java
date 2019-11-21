package com.kagu.submitthree.FavPackage;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kagu.submitthree.DetailActivity.DetailTmdbTvActivity;
import com.kagu.submitthree.Interface.GetTmdbCallback;
import com.kagu.submitthree.Model.Tmdb;
import com.kagu.submitthree.MoviePackage.MovieDB;
import com.kagu.submitthree.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.kagu.submitthree.DetailActivity.DetailTmdbTvActivity.REQUEST_1;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovie extends Fragment implements GetTmdbCallback {
    private static final String ID_FAV_MOVIE = "ID_FAV_MOVIE";
    private RecyclerView rvFav;
    private FavMovieAdapter favMovieAdapter;
    private MovieDB movieDB;


    public FavMovie() {
        // Required empty public constructor
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadFavM(movieDB, this).execute();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFav = view.findViewById(R.id.rv_favM);
        rvFav.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFav.setHasFixedSize(true);

        favMovieAdapter = new FavMovieAdapter(this);
        rvFav.setAdapter(favMovieAdapter);

        movieDB = MovieDB.getMovieDB(getActivity());
        movieDB.open();

        if (savedInstanceState == null) {
            new loadFavM(movieDB, this).execute();
        } else {
            ArrayList<Tmdb> listM = savedInstanceState.getParcelableArrayList(ID_FAV_MOVIE);
            if (listM != null) {
                favMovieAdapter.setListFavM(listM);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == DetailTmdbTvActivity.REQUEST_INSERT) {
                if (resultCode == DetailTmdbTvActivity.RESULT_INSERT) {
                    Tmdb tmdb = data.getParcelableExtra(DetailTmdbTvActivity.MOVIE_KEY);
                    favMovieAdapter.addFavM(tmdb);
                    rvFav.smoothScrollToPosition(favMovieAdapter.getItemCount() - 1);
                    Toast.makeText(getContext(), R.string.fav_save, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_1) {
                if (resultCode == DetailTmdbTvActivity.RESULT_DEL) {
                    int i = data.getIntExtra(DetailTmdbTvActivity.FAV_KEY, 0);
                    favMovieAdapter.delFavM(i);
                    Toast.makeText(getContext(), R.string.fav_del, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onError() {

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ID_FAV_MOVIE, favMovieAdapter.getListFavM());
    }

    @Override
    public void onSuccess(ArrayList<Tmdb> tmdbs) {
        favMovieAdapter.setListFavM(tmdbs);
    }

    private class loadFavM extends AsyncTask<Void, Void, ArrayList<Tmdb>> {
        private final WeakReference<MovieDB> dbWeakReference;
        private final WeakReference<GetTmdbCallback> callbackWeakReference;

        private loadFavM(MovieDB movieDB, GetTmdbCallback callback) {
            dbWeakReference = new WeakReference<>(movieDB);
            callbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callbackWeakReference.get().onError();
        }

        @Override
        protected void onPostExecute(ArrayList<Tmdb> tmdbs) {
            super.onPostExecute(tmdbs);
            callbackWeakReference.get().onSuccess(tmdbs);
        }

        @Override
        protected ArrayList<Tmdb> doInBackground(Void... voids) {
            return dbWeakReference.get().getFavM();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_movie, container, false);
    }
}
