package com.example.practice_tvshowapp.repository

import com.example.practice_tvshowapp.api.TvShowService
import com.example.practice_tvshowapp.models.tvshows.TvShow
import dagger.Binds
import retrofit2.Response
import javax.inject.Inject

class TvShowRepository
@Inject constructor(
    private val tvShowService: TvShowService
) {
    suspend fun getAllTvShows(): Response<TvShow> {
        return tvShowService.getTvShows()
    }

    suspend fun getTvShowsOnYesterday(date: String) : Response<TvShow> {
        return tvShowService.getTvShowOnYesterday(date = date)
    }
}