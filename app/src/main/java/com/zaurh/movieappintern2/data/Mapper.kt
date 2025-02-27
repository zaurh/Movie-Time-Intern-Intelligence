package com.zaurh.movieappintern2.data

import com.zaurh.movieappintern2.data.models.discover_movie.DiscoverResultDto
import com.zaurh.movieappintern2.data.models.movie_details.MovieDetailsDto
import com.zaurh.movieappintern2.data.models.now_playing_movies.NowPlayingResultDto
import com.zaurh.movieappintern2.data.models.popular_movies.PopularMoviesResultDto
import com.zaurh.movieappintern2.data.models.top_rated_movies.TopRatedDto
import com.zaurh.movieappintern2.data.models.top_rated_movies.TopRatedResultDto
import com.zaurh.movieappintern2.data.models.upcoming_movies.UpcomingMoviesResultDto
import com.zaurh.movieappintern2.domain.models.Movie
import com.zaurh.movieappintern2.domain.models.MovieDetails
import com.zaurh.movieappintern2.util.Constants.IMAGE_URL

fun time(runtime: Int): String {
    val hours = runtime / 60
    val minutes = runtime % 60
    return "${hours}h ${minutes}m"
}

fun MovieDetailsDto.toMovieDetails() : MovieDetails {
    val posterImage = "$IMAGE_URL/$poster_path"
    return MovieDetails(
        id = id,
        title = title,
        poster = posterImage,
        time = time(runtime),
        genres = genres,
        overview = overview,
        releaseDate = release_date,
        vote = vote_average
    )
}

fun MovieDetails.toFavoriteData() = com.zaurh.movieappintern2.data.models.firebase.FavoriteData(
    movieId = id,
    poster = poster
)

fun PopularMoviesResultDto.toMovie(): Movie {
    val posterUrl = if (poster_path == null) "" else "$IMAGE_URL/$poster_path"
    return Movie(
        id = id,
        title = title,
        poster = posterUrl,
        overview = overview,
        releaseDate = release_date,
        vote = vote_average
    )
}

fun NowPlayingResultDto.toMovie() : Movie {
    val posterImage = "$IMAGE_URL/$poster_path"
    return Movie(
        id = id,
        title = title,
        poster = posterImage,
        overview = overview,
        releaseDate = release_date,
        vote = vote_average
    )
}

fun UpcomingMoviesResultDto.toMovie() : Movie {
    val posterImage = "$IMAGE_URL/$poster_path"
    return Movie(
        id = id,
        title = title,
        poster = posterImage,
        overview = overview,
        releaseDate = release_date,
        vote = vote_average
    )
}

fun TopRatedResultDto.toMovie() : Movie {
    val posterImage = "$IMAGE_URL/$poster_path"
    return Movie(
        id = id,
        title = title,
        poster = posterImage,
        overview = overview,
        releaseDate = release_date,
        vote = vote_average
    )
}

fun DiscoverResultDto.toMovie() : Movie {
    val posterImage = "$IMAGE_URL/$poster_path"
    return Movie(
        id = id,
        title = title,
        poster = posterImage,
        overview = overview,
        releaseDate = release_date,
        vote = vote_average
    )
}