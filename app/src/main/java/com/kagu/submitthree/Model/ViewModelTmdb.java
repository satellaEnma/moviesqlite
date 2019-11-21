package com.kagu.submitthree.Model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kagu.submitthree.BuildConfig;
import com.kagu.submitthree.Interface.TmdbMovieApi;
import com.kagu.submitthree.Interface.TmdbTvApi;
import com.kagu.submitthree.MoviePackage.LibraryMovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewModelTmdb extends ViewModel {
    public static final String API_TMDB = BuildConfig.API_TMDB;
    public static final String URL_TMDB = "https://api.themoviedb.org/3/";
    private MutableLiveData<List<Tmdb>> liveTmdb = new MutableLiveData<>();
    private MutableLiveData<List<Tmdb>> liveTmdbTv = new MutableLiveData<>();

    public LiveData<List<Tmdb>> getTmdbTv(Context context){
        dataTmdbTv(context);
        return liveTmdbTv;
    }

    public LiveData<List<Tmdb>> getTmdb(Context context){
        dataTmdb(context);
        return liveTmdb;
    }

    public void dataTmdbTv(final Context context){
        Retrofit retrofitTv = new Retrofit.Builder().baseUrl(URL_TMDB).addConverterFactory(GsonConverterFactory.create()).build();
        final TmdbTvApi tmdbTvApi = retrofitTv.create(TmdbTvApi.class);
        Call<TmdbResponse> callTv = tmdbTvApi.getTopRateTv(24,API_TMDB);
        callTv.enqueue(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {
                liveTmdbTv.setValue(response.body().getTmdbs());
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {

            }
        });
    }

    public void dataTmdb(final Context context) {
        Retrofit retrofitTmdb = new Retrofit.Builder().baseUrl(URL_TMDB).addConverterFactory(GsonConverterFactory.create()).build();
        final TmdbMovieApi tmdbMovieApi = retrofitTmdb.create(TmdbMovieApi.class);
        Call<TmdbResponse> callLibrary = tmdbMovieApi.getTopRateMovie(24,API_TMDB);
        callLibrary.enqueue(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {
                liveTmdb.setValue(response.body().getTmdbs());
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {

            }
        });
    }

}

