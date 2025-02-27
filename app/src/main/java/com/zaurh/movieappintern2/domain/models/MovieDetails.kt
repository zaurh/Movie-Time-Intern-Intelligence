package com.zaurh.movieappintern2.domain.models

import com.zaurh.movieappintern2.data.models.movie_details.Genre

data class MovieDetails(
    val id: Int = 0,
    val title: String = "",
    val genres: List<Genre> = emptyList(),
    val time: String = "",
    val poster: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val vote: Double = 0.0
)

