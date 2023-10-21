package com.example.practice_tvshowapp.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

@ProvidedTypeConverter
class StringListTypeConverter(
    private val gson: Gson
) {
    @TypeConverter
    fun jsonToString(value: String): List<String> {
        return gson.fromJson(value, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun listToJson(value: List<String>): String {
        return gson.toJson(value)
    }
}