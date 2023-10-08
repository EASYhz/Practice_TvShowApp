package com.example.practice_tvshowapp.models.tvshows

import java.io.Serializable

data class Externals(
    val imdb: String,
    val thetvdb: Int,
    val tvrage: Int
) : Serializable