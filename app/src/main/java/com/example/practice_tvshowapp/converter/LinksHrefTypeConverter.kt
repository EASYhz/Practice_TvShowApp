//package com.example.practice_tvshowapp.converter
//
//import androidx.room.ProvidedTypeConverter
//import androidx.room.TypeConverter
//import com.example.practice_tvshowapp.models.tvshows.LinksHref
//import com.google.gson.Gson
//
//@ProvidedTypeConverter
//class LinksHrefTypeConverter(private val gson: Gson) {
//    @TypeConverter
//    fun jsonToLinksHref(value: String) : LinksHref = gson.fromJson(value, LinksHref::class.java)
//
//    @TypeConverter
//    fun linksHrefToJson(value: LinksHref) : String = gson.toJson(value)
//}