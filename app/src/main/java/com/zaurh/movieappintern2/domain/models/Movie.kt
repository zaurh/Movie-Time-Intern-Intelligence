package com.zaurh.movieappintern2.domain.models

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val poster: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val vote: Double = 0.0
)
