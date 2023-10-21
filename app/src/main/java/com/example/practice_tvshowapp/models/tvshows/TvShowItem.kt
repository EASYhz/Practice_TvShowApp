package com.example.practice_tvshowapp.models.tvshows

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import java.io.Serializable

@Entity(tableName = "tv_show")
data class TvShowItem (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
//    val _links: Links,
    val averageRuntime: Int?,
    val ended: String?,
    val genres: List<String>?,
    val airdate: String?,
    val image: Image?,
    val language: String?,
    val name: String?,
    val officialSite: String?,
    val premiered: String?,
    @Embedded
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    val rating: Rating?,
    val runtime: Int?,
    val schedule: Schedule?,
    val status: String?,
    val summary: String?,
    val type: String?,
    val updated: Int?,
    val url: String?,
    val weight: Int?,
) : Serializable