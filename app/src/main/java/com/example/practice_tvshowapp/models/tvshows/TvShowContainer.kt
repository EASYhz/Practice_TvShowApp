package com.example.practice_tvshowapp.models.tvshows

data class TvShowContainer(
    val id: Int,
    val subject: String,
    val tvShows: List<TvShowItem>
)
