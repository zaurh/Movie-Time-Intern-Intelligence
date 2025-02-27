package com.zaurh.movieappintern2.data.models.upcoming_movies

data class UpcomingMoviesDto(
    val dates: com.zaurh.movieappintern2.data.models.upcoming_movies.Dates,
    val page: Int,
    val results: List<com.zaurh.movieappintern2.data.models.upcoming_movies.UpcomingMoviesResultDto>,
    val total_pages: Int,
    val total_results: Int
)