package com.example.practice_tvshowapp.models.tvshows

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Rating(
    @PrimaryKey(autoGenerate = true)
    val filedId: Int,
    val average: Double
) : Serializable