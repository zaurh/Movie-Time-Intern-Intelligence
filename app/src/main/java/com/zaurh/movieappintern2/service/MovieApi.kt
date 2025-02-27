package com.zaurh.movieappintern2.service

import com.zaurh.movieappintern2.data.models.discover_movie.DiscoverDto
import com.zaurh.movieappintern2.data.models.popular_movies.PopularMoviesDto
import com.zaurh.movieappintern2.data.models.genres.GenresDto
import com.zaurh.movieappintern2.data.models.movie_details.MovieDetailsDto
import com.zaurh.movieappintern2.data.models.movie_video.MovieVideoDto
import com.zaurh.movieappintern2.data.models.now_playing_movies.NowPlayingDto
import com.zaurh.movieappintern2.data.models.top_rated_movies.TopRatedDto
import com.zaurh.movieappintern2.data.models.upcoming_movies.UpcomingMoviesDto
import com.zaurh.movieappintern2.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = API_KEY
    ): GenresDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): UpcomingMoviesDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): PopularMoviesDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): NowPlayingDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): TopRatedDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("with_genres") withGenres: Int,
        @Query("page") page: Int = 1
    ): DiscoverDto


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieVideoDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): PopularMoviesDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): PopularMoviesDto

}