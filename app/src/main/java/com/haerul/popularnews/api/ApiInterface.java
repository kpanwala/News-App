package com.haerul.popularnews.api;

import com.haerul.popularnews.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<News> getNews(

            @Query("country") String country ,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<News> getCatNews(

            @Query("country") String country ,
            @Query("category") String category,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<News> getNewsSearch(

        @Query("q") String keyword,
        @Query("language") String language,
        @Query("sortBy") String sortBy,
        @Query("apiKey") String apiKey

    );

}
