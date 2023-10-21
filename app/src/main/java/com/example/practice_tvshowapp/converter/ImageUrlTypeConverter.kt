package com.example.practice_tvshowapp.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.practice_tvshowapp.models.tvshows.Image
import com.google.gson.Gson

@ProvidedTypeConverter
class ImageUrlTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun jsonToImage(value: String) : Image {
        return gson.fromJson(value, Image::class.java)
    }

    @TypeConverter
    fun imageToJson(value: Image) : String {
        return gson.toJson(value)
    }
}