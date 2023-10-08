package com.example.practice_tvshowapp.models.tvshows

import java.io.Serializable

data class Image(
    val medium: String,
    val original: String
) : Serializable