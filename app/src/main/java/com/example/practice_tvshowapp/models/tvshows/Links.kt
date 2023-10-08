package com.example.practice_tvshowapp.models.tvshows

import java.io.Serializable

data class Links(
    val nextepisode: Nextepisode,
    val previousepisode: Previousepisode,
    val self: Self
) : Serializable