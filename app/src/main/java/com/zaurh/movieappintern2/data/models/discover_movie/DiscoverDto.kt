package com.zaurh.movieappintern2.data.models.discover_movie

data class DiscoverDto(
    val page: Int,
    val results: List<DiscoverResultDto>,
    val total_pages: Int,
    val total_results: Int
)