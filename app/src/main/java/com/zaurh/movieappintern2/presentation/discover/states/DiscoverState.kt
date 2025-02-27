package com.zaurh.movieappintern2.presentation.discover.states

import com.zaurh.movieappintern2.domain.models.Movie

data class DiscoverState(
    val page: Int = 1,
    val totalPages: Int = 0,
    val totalResults: Int = 0,
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)