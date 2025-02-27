package com.zaurh.movieappintern2.data.repository

import com.zaurh.movieappintern2.data.models.discover_movie.DiscoverDto
import com.zaurh.movieappintern2.data.models.popular_movies.PopularMoviesDto
import com.zaurh.movieappintern2.data.toMovie
import com.zaurh.movieappintern2.data.toMovieDetails
import com.zaurh.movieappintern2.domain.models.Movie
import com.zaurh.movieappintern2.domain.models.MovieDetails
import com.zaurh.movieappintern2.domain.repository.MovieRepository
import com.zaurh.movieappintern2.service.MovieApi
import com.zaurh.movieappintern2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override fun searchMovies(query: String, page: Int): Flow<Resource<PopularMoviesDto>> = flow{
        emit(Resource.Loading())
        try {
            val searchMovies = api.searchMovies(query = query, page = page)
            emit(Resource.Success(searchMovies))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getMovieVideo(movieId: Int): Flow<Resource<com.zaurh.movieappintern2.data.models.movie_video.MovieVideoDto>> = flow {
        emit(Resource.Loading())
        try {
            val movieVideo = api.getMovieVideo(movieId = movieId)
            emit(Resource.Success(movieVideo))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getGenres(): Flow<Resource<com.zaurh.movieappintern2.data.models.genres.GenresDto>> = flow {
        emit(Resource.Loading())
        try {
            val genres = api.getGenres()
            emit(Resource.Success(genres))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getUpcomingMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val upcomingMovies = api.getUpcomingMovies()
            emit(Resource.Success(upcomingMovies.results.map { it.toMovie() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val popularMovies = api.getPopularMovies()
            emit(Resource.Success(popularMovies.results.map { it.toMovie() }))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getNowPlayingMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val nowPlayingMovies = api.getNowPlayingMovies()
            emit(Resource.Success(nowPlayingMovies.results.map { it.toMovie() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getTopRatedMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val topRatedMovies = api.getTopRatedMovies()
            emit(Resource.Success(topRatedMovies.results.map { it.toMovie() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun discoverMovies(withGenres: Int, page: Int): Flow<Resource<DiscoverDto>> = flow {
        emit(Resource.Loading())
        try {
            val discoverMovies = api.discoverMovies(withGenres = withGenres, page = page)
            emit(Resource.Success(discoverMovies))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> = flow {
        emit(Resource.Loading())
        try {
            val movieDetails = api.getMovieDetails(movieId = movieId)
            emit(Resource.Success(movieDetails.toMovieDetails()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getRecommendations(movieId: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val recommendedMovies = api.getRecommendations(movieId = movieId)
            emit(Resource.Success(recommendedMovies.results.map { it.toMovie() }))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

}