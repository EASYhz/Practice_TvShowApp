package com.example.practice_tvshowapp.models.casts

data class CastItem(
    val character: Character,
    val person: Person,
    val self: Boolean,
    val voice: Boolean
)