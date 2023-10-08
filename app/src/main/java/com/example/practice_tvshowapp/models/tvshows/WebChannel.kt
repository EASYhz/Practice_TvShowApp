package com.example.practice_tvshowapp.models.tvshows

import java.io.Serializable

data class WebChannel(
    val country: Country,
    val id: Int,
    val name: String,
    val officialSite: String
) : Serializable