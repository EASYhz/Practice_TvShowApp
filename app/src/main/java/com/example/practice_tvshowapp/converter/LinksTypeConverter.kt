package com.example.practice_tvshowapp.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.practice_tvshowapp.models.casts.Links
import com.google.gson.Gson

//@ProvidedTypeConverter
class LinksTypeConverter(private val gson: Gson) {
//    @TypeConverter
//    fun jsonToLinks(value: String): Links {
//        return gson.fromJson(value, Links::class.java)
//    }
//
//    @TypeConverter
//    fun linksToJson(value: Links): String = gson.toJson(value)

}