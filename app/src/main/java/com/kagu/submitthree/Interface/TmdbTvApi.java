package com.kagu.submitthree.Interface;

import com.kagu.submitthree.Model.TmdbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbTvApi {
    @GET("tv/top_rated")
    Call<TmdbResponse> getTopRateTv(@Query("page") int page,
                                    @Query("api_key") String apiKey);
}
