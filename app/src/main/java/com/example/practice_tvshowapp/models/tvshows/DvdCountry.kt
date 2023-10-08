package com.example.practice_tvshowapp.models.tvshows

import java.io.Serializable

data class DvdCountry(
    val code: String,
    val name: String,
    val timezone: String
) : Serializable