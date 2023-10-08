package com.example.practice_tvshowapp.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.practice_tvshowapp.api.TvShowService
import com.example.practice_tvshowapp.models.casts.Cast
import com.example.practice_tvshowapp.models.episodes.Episode
import com.example.practice_tvshowapp.models.tvshows.TvShow
import com.example.practice_tvshowapp.models.tvshows.TvShowContainer
import retrofit2.Response
import javax.inject.Inject

class TvShowRepository
@Inject constructor(
    private val tvShowService: TvShowService
) {
    @SuppressLint("SuspiciousIndentation")
    suspend fun getAllTvShows(): ArrayList<TvShowContainer> {
        val result: ArrayList<TvShowContainer> = ArrayList()
            (0..5).forEach { page ->
                tvShowService.getTvShows(page).let { response ->
                    if (response.isSuccessful) {
                        result.add(
                            TvShowContainer(
                                id = page,
                                subject = "${page+1}번째 목록",
                                tvShows = response.body()!!
                            )
                        )
                    } else {
                        Log.d("TvShowViewModel >> ", "getAllTvShows Error: ${response.code()}")
                    }
                }
            }
        return result
    }

    suspend fun getWebTvShowOnYesterday(date: String) : Response<TvShow> {
        return tvShowService.getWebTvShowOnYesterday(date = date)
    }

    suspend fun getEpisodes(tvShowId: Int) : Response<Episode> {
        return tvShowService.getEpisodes(tvShowId = tvShowId)
    }

    suspend fun getCasts(tvShowId: Int) : Response<Cast> {
        return tvShowService.getCasts(tvShowId = tvShowId)
    }
}