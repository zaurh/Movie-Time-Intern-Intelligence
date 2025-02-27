package com.zaurh.movieappintern2.presentation.main.states

import com.zaurh.movieappintern2.data.models.genres.GenresDto

data class GenresState(
    val isLoading: Boolean = false,
    val genresDto: GenresDto? = null,
    val error: String = ""
)