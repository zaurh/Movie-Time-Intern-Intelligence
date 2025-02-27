package com.zaurh.movieappintern2.data.models.now_playing_movies

data class NowPlayingDto(
    val dates: Dates,
    val page: Int,
    val results: List<NowPlayingResultDto>,
    val total_pages: Int,
    val total_results: Int
)