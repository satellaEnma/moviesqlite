package com.kagu.submitthree.MoviePackage;

import com.kagu.submitthree.Interface.GetTmdbCallback;
import com.kagu.submitthree.Interface.TmdbMovieApi;
import com.kagu.submitthree.Model.TmdbResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibraryMovie {
    private static final String URL = "https://api.themoviedb.org/3/";

    private static LibraryMovie libraryMovie;

    private static TmdbMovieApi tmdbMovieApi;

    private LibraryMovie(TmdbMovieApi api) {
        this.tmdbMovieApi = api;
    }

    public static LibraryMovie getInstance() {
        if (tmdbMovieApi == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

            libraryMovie = new LibraryMovie(retrofit.create(TmdbMovieApi.class));
        }
        return libraryMovie;
    }
}
