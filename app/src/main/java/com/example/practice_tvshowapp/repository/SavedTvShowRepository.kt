package com.example.practice_tvshowapp.repository

import com.example.practice_tvshowapp.database.dao.TvShowItemDao
import com.example.practice_tvshowapp.models.tvshows.TvShowItem
import javax.inject.Inject

class SavedTvShowRepository
@Inject constructor(
    private val dao: TvShowItemDao
) {
    suspend fun insertTvShow(tvShowItem: TvShowItem) = dao.insertTvShow(tvShowItem)

}