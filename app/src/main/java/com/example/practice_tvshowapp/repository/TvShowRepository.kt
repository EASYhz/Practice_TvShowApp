package com.example.practice_tvshowapp.repository

import com.example.practice_tvshowapp.api.TvShowService
import com.example.practice_tvshowapp.models.episodes.TvShowEpisode
import com.example.practice_tvshowapp.models.tvshows.TvShow
import retrofit2.Response
import javax.inject.Inject

class TvShowRepository
@Inject constructor(
    private val tvShowService: TvShowService
) {
    suspend fun getAllTvShows(): Response<TvShow> {
        return tvShowService.getTvShows()
    }

    suspend fun getWebTvShowOnYesterday(date: String) : Response<TvShow> {
        return tvShowService.getWebTvShowOnYesterday(date = date)
    }

    suspend fun getTvShowEpisodes(tvShowId: Int) : Response<TvShowEpisode> {
        return tvShowService.getTvShowEpisodes(tvShowId = tvShowId)
    }
}