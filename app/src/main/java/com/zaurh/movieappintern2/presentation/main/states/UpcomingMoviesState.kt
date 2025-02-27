package com.zaurh.movieappintern2.presentation.main.states

import com.zaurh.movieappintern2.domain.models.Movie

data class UpcomingMoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)