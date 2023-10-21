package com.example.practice_tvshowapp.models.tvshows

import java.io.Serializable

data class Links(
    val nextepisode: LinksHref,
    val previousepisode: LinksHref,
    val self: LinksHref
) : Serializable