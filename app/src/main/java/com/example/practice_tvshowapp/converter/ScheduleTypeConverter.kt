package com.example.practice_tvshowapp.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.practice_tvshowapp.models.tvshows.Schedule
import com.google.gson.Gson

@ProvidedTypeConverter
class ScheduleTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun jsonToSchedule(value: String): Schedule = gson.fromJson(value, Schedule::class.java)

    @TypeConverter
    fun scheduleToJson(value: Schedule): String = gson.toJson(value)
}