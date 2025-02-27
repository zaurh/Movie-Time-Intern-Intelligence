package com.zaurh.movieappintern2.data.models.movie_video

data class MovieVideoDto(
    val id: Int,
    val results: List<com.zaurh.movieappintern2.data.models.movie_video.MovieVideoResultDto>
)