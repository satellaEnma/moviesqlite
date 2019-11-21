package com.kagu.submitthree.TvPackage;

import com.kagu.submitthree.Interface.GetTmdbCallback;
import com.kagu.submitthree.Interface.TmdbTvApi;
import com.kagu.submitthree.Model.TmdbResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibraryTv {
    private static LibraryTv libraryTv;
    private static TmdbTvApi tmdbTvApi;

    public LibraryTv(TmdbTvApi tmdbTvApi) {
        this.tmdbTvApi = tmdbTvApi;
    }

    public static LibraryTv getInstance() {
        if (tmdbTvApi == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https:api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();

            libraryTv = new LibraryTv(retrofit.create(TmdbTvApi.class));
        }
        return libraryTv;
    }
}
