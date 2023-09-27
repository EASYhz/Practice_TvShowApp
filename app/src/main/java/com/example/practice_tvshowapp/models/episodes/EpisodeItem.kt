package com.example.practice_tvshowapp.models.episodes

data class EpisodeItem(
    val _links: Links,
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val image: Image,
    val name: String,
    val number: Int,
    val rating: Rating,
    val runtime: Int,
    val season: Int,
    val summary: String,
    val type: String,
    val url: String
)