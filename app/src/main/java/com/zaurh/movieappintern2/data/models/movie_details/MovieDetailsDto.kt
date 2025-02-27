package com.zaurh.movieappintern2.data.models.movie_details

data class MovieDetailsDto(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Long,
    val genres: List<com.zaurh.movieappintern2.data.models.movie_details.Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<com.zaurh.movieappintern2.data.models.movie_details.ProductionCompany>,
    val production_countries: List<com.zaurh.movieappintern2.data.models.movie_details.ProductionCountry>,
    val release_date: String,
    val revenue: Long,
    val runtime: Int,
    val spoken_languages: List<com.zaurh.movieappintern2.data.models.movie_details.SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)




