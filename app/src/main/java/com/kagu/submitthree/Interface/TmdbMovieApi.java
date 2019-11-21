package com.kagu.submitthree.Interface;

import com.kagu.submitthree.Model.TmdbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbMovieApi {

    @GET("movie/top_rated")
    Call<TmdbResponse> getTopRateMovie(@Query("page") int page,
                                       @Query("api_key") String apiKey);
}
