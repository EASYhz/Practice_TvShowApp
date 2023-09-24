package com.example.practice_tvshowapp.di

import com.example.practice_tvshowapp.api.TvShowService
import com.example.practice_tvshowapp.helper.Constants
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun tvShowService() : TvShowService = getRetrofitInstance().create(TvShowService::class.java)


    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}