package com.zaurh.movieappintern2.domain.repository

import com.zaurh.movieappintern2.data.models.discover_movie.DiscoverDto
import com.zaurh.movieappintern2.domain.models.MovieDetails
import com.zaurh.movieappintern2.domain.models.Movie
import com.zaurh.movieappintern2.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun searchMovies(query: String, page: Int): Flow<Resource<com.zaurh.movieappintern2.data.models.popular_movies.PopularMoviesDto>>
    fun getMovieVideo(movieId: Int): Flow<Resource<com.zaurh.movieappintern2.data.models.movie_video.MovieVideoDto>>
    fun getGenres(): Flow<Resource<com.zaurh.movieappintern2.data.models.genres.GenresDto>>

    fun getUpcomingMovies(): Flow<Resource<List<Movie>>>
    fun getPopularMovies(): Flow<Resource<List<Movie>>>
    fun getNowPlayingMovies(): Flow<Resource<List<Movie>>>
    fun getTopRatedMovies(): Flow<Resource<List<Movie>>>
    fun discoverMovies(withGenres: Int, page: Int): Flow<Resource<DiscoverDto>>

    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
    fun getRecommendations(movieId: Int): Flow<Resource<List<Movie>>>

}