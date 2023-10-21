package com.example.practice_tvshowapp.di

import android.content.Context
import androidx.room.Room
import com.example.practice_tvshowapp.converter.ImageUrlTypeConverter
import com.example.practice_tvshowapp.converter.ScheduleTypeConverter
import com.example.practice_tvshowapp.converter.StringListTypeConverter
import com.example.practice_tvshowapp.database.TvShowItemDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideTvShowItemDataBase(
        @ApplicationContext context: Context,
        gson:Gson
    ) = Room.databaseBuilder(
        context,
        TvShowItemDatabase::class.java,
        "tv_show"
    )
        .addTypeConverter(ImageUrlTypeConverter(gson))
        .addTypeConverter(ScheduleTypeConverter(gson))
        .addTypeConverter(StringListTypeConverter(gson))
        .build()

    @Provides
    fun provideTvShowDao(
        database: TvShowItemDatabase
    ) = database.dao()
}