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
import com.kagu.submitthree.R;
import com.kagu.submitthree.TvPackage.TvsDB;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.kagu.submitthree.DetailActivity.DetailTmdbTvActivity.REQUEST_1;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTv extends Fragment implements GetTmdbCallback {
    private static final String ID_FAV_TV = "ID_FAV_TV";
    private RecyclerView rvFavTv;
    private FavTvAdapter favTvAdapter;
    private TvsDB tvsDB;

    public FavTv() {
        // Required empty public constructor
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ID_FAV_TV, favTvAdapter.getListFavTv());
    }

    @Override
    public void onSuccess(ArrayList<Tmdb> tmdbs) {
        favTvAdapter.setListFavTv(tmdbs);
    }

    private class loadFavTv extends AsyncTask<Void, Void, ArrayList<Tmdb>> {
        private final WeakReference<TvsDB> tmdbWeakReference;
        private final WeakReference<GetTmdbCallback> callbackWeakReference;

        private loadFavTv(TvsDB tvsDB, GetTmdbCallback callback) {
            tmdbWeakReference = new WeakReference<>(tvsDB);
            callbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<Tmdb> doInBackground(Void... voids) {
            return tmdbWeakReference.get().getFavTv();
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavTv = view.findViewById(R.id.rv_favTv);
        rvFavTv.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavTv.setHasFixedSize(true);

        favTvAdapter = new FavTvAdapter(this);
        rvFavTv.setAdapter(favTvAdapter);

        tvsDB = TvsDB.getTvsDB(getActivity());
        tvsDB.openTv();

        if (savedInstanceState == null) {
            new loadFavTv(tvsDB, this).execute();
        } else {
            ArrayList<Tmdb> listTv = savedInstanceState.getParcelableArrayList(ID_FAV_TV);
            if (listTv != null) {
                favTvAdapter.setListFavTv(listTv);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == DetailTmdbTvActivity.REQUEST_INSERT) {
                if (resultCode == DetailTmdbTvActivity.RESULT_INSERT) {
                    Tmdb tmdb = data.getParcelableExtra(DetailTmdbTvActivity.TV_KEY);
                    favTvAdapter.addFavTv(tmdb);
                    rvFavTv.smoothScrollToPosition(favTvAdapter.getItemCount() - 1);
                    Toast.makeText(getContext(), R.string.fav_save, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_1) {
                if (resultCode == DetailTmdbTvActivity.RESULT_DEL) {
                    int i = data.getIntExtra(DetailTmdbTvActivity.FAV_KEY, 0);
                    favTvAdapter.delFavTv(i);
                    Toast.makeText(getContext(), R.string.fav_del, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadFavTv(tvsDB, this).execute();
    }
}
