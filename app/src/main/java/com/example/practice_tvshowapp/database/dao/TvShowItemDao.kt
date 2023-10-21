package com.example.practice_tvshowapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.practice_tvshowapp.models.tvshows.TvShowItem

@Dao
interface TvShowItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShowItem: TvShowItem)
}