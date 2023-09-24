package com.example.practice_tvshowapp.api

import com.example.practice_tvshowapp.models.tvshows.TvShow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowService {
    @GET("/shows")
    suspend fun getTvShows(): Response<TvShow>

    @GET("/schedule/web")
    suspend fun getWebTvShowOnYesterday(@Query("date") date: String) : Response<TvShow>   // 어제 tvShow 보기

}

