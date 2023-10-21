package com.example.practice_tvshowapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practice_tvshowapp.converter.ImageUrlTypeConverter
import com.example.practice_tvshowapp.converter.ScheduleTypeConverter
import com.example.practice_tvshowapp.converter.StringListTypeConverter
import com.example.practice_tvshowapp.database.dao.TvShowItemDao
import com.example.practice_tvshowapp.models.tvshows.TvShowItem

@Database(
    entities = [TvShowItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        ImageUrlTypeConverter::class,
        ScheduleTypeConverter::class,
        StringListTypeConverter::class,
    ]
)
abstract class TvShowItemDatabase: RoomDatabase() {
    abstract fun dao(): TvShowItemDao
}