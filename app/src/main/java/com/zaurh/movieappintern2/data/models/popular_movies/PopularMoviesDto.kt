package com.zaurh.movieappintern2.data.models.popular_movies

data class PopularMoviesDto(
    val page: Int,
    val results: List<PopularMoviesResultDto>,
    val total_pages: Int,
    val total_results: Int
)