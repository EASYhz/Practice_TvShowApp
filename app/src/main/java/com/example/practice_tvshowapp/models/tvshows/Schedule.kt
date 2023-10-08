package com.example.practice_tvshowapp.models.tvshows

import java.io.Serializable

data class Schedule(
    val days: List<String>,
    val time: String
) : Serializable