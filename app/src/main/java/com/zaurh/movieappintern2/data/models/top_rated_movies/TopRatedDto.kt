package com.zaurh.movieappintern2.data.models.top_rated_movies

data class TopRatedDto(
    val page: Int,
    val results: List<TopRatedResultDto>,
    val total_pages: Int,
    val total_results: Int
)