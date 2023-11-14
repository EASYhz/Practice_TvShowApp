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
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RoomModule::class]
)object RoomModuleTest {
    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    fun provideTvShowItemDataBase(
        @ApplicationContext context: Context,
        gson: Gson
    ) =
        Room.inMemoryDatabaseBuilder(
            context,
            TvShowItemDatabase::class.java
        )
            .addTypeConverter(ImageUrlTypeConverter(gson))
            .addTypeConverter(ScheduleTypeConverter(gson))
            .addTypeConverter(StringListTypeConverter(gson))
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideTvShowDao(
        database: TvShowItemDatabase
    ) = database.dao()
}