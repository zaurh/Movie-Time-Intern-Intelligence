package com.zaurh.movieappintern2.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaurh.movieappintern2.domain.models.MovieDetails
import com.zaurh.movieappintern2.domain.models.Movie
import com.zaurh.movieappintern2.domain.repository.DatabaseRepository
import com.zaurh.movieappintern2.domain.repository.MovieRepository
import com.zaurh.movieappintern2.domain.repository.ReviewRepository
import com.zaurh.movieappintern2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

data class MovieDetailsState(
    val movieId: Int = 0,
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val message: String = ""
)

data class RecommendedMoviesState(
    val isLoading: Boolean = false,
    val recommendedMovies: List<Movie> = listOf(),
    val message: String = ""
)

data class MovieVideoState(
    val isLoading: Boolean = false,
    val movieVideos: List<com.zaurh.movieappintern2.data.models.movie_video.MovieVideoResultDto> = listOf(),
    val message: String = ""
)

data class ReviewState(
    val allReviews: List<com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData> = listOf(),
    val loading: Boolean = false,
    val message: String = "",
    val rate: com.zaurh.movieappintern2.data.models.firebase.reviews.Rate = com.zaurh.movieappintern2.data.models.firebase.reviews.Rate.NONE,
    val opinion: String = "",
    val alertVisible: Boolean = false
)

data class AddToFavoriteState(
    val alreadyAdded: Boolean = false,
    val isLoading: Boolean = false,
    val message: String = ""
)

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val reviewRepository: ReviewRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _movieDetailsState = MutableStateFlow(MovieDetailsState())
    val movieDetailsState = _movieDetailsState.asStateFlow()

    private val _recommendedMoviesState = MutableStateFlow(RecommendedMoviesState())
    val recommendedMoviesState = _recommendedMoviesState.asStateFlow()

    private val _movieVideoState = MutableStateFlow(MovieVideoState())
    val movieVideoState = _movieVideoState.asStateFlow()

    private val _reviewState = MutableStateFlow(ReviewState())
    val reviewState = _reviewState.asStateFlow()

    private val _addToFavoriteState = MutableStateFlow(AddToFavoriteState())
    val addToFavoriteState = _addToFavoriteState.asStateFlow()


    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieDetails(movieId).collect { result ->
                Log.d("dsalkjladksjklda", "${result.message}")
                when (result) {
                    is Resource.Error -> {
                        _movieDetailsState.update {
                            it.copy(
                                message = it.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _movieDetailsState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {

                        _movieDetailsState.update {
                            it.copy(
                                movieDetails = result.data,
                                isLoading = false,
                                movieId = movieId
                            )
                        }
                    }
                }
            }
            getRecommendations(movieId)
            getMovieVideo(movieId)
        }
    }

    fun checkIfAlreadyAdded(movie: com.zaurh.movieappintern2.data.models.firebase.FavoriteData, userData: com.zaurh.movieappintern2.data.models.firebase.UserData) {
        if (userData.favoriteMovies.contains(movie)) {
            _addToFavoriteState.update { it.copy(alreadyAdded = true) }
        }else{
            _addToFavoriteState.update { it.copy(alreadyAdded = false) }
        }
    }

    private fun getRecommendations(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getRecommendations(movieId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _recommendedMoviesState.value =
                            RecommendedMoviesState(message = result.message ?: "")
                    }

                    is Resource.Loading -> {
                        _recommendedMoviesState.value = RecommendedMoviesState(isLoading = true)
                    }

                    is Resource.Success -> {
                        val recommendedMovies = result.data ?: listOf()
                        if (recommendedMovies.isEmpty()) {
                            _recommendedMoviesState.value =
                                RecommendedMoviesState(message = "No similiar movies found.")
                            return@collect
                        }
                        _recommendedMoviesState.value =
                            RecommendedMoviesState(recommendedMovies = recommendedMovies)
                    }
                }
            }
        }
    }

    private fun getMovieVideo(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieVideo(movieId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieVideoState.value = MovieVideoState(message = result.message ?: "")
                    }

                    is Resource.Loading -> {
                        _movieVideoState.value = MovieVideoState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d("Sdajldsajldsa", "${result.data}")
                        val movieVideos = result.data?.results ?: listOf()
                        if (movieVideos.isEmpty()) {
                            _movieVideoState.value = MovieVideoState(message = "No trailer found.")
                            return@collect
                        }
                        _movieVideoState.value = MovieVideoState(movieVideos = movieVideos)

                    }
                }
            }
        }

    }

    fun onRateValueChange(value: String) {
        _reviewState.update {
            it.copy(opinion = value)
        }
    }

    fun onAlertDismiss() {
        _reviewState.update { it.copy(alertVisible = false, rate = com.zaurh.movieappintern2.data.models.firebase.reviews.Rate.NONE, opinion = "") }
    }

    fun onAlertShow() {
        _reviewState.update {
            it.copy(alertVisible = true)
        }
    }

    fun selectThumb(rate: com.zaurh.movieappintern2.data.models.firebase.reviews.Rate) {
        _reviewState.update { it.copy(rate = rate) }
    }

    fun addReview(
        author: com.zaurh.movieappintern2.data.models.firebase.UserData
    ) {
        val movie = movieDetailsState.value.movieDetails

        val reviewData = com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData(
            id = UUID.randomUUID().toString(),
            author = author,
            opinion = reviewState.value.opinion,
            rate = reviewState.value.rate,
            movie = movie
        )
        viewModelScope.launch(Dispatchers.IO) {
            reviewRepository.addReview(reviewData).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        Log.d("SDAADSDSAASD", "addReview: ${result.message}")
                    }

                    is Resource.Loading -> {
                        Log.d("SDAADSDSAASD", "addReview: Loading")
                    }

                    is Resource.Success -> {
                        getReviews()
                        Log.d("SDAADSDSAASD", "addReview: Success")
                    }
                }
            }
        }
    }

    fun addToFavorites(movie: com.zaurh.movieappintern2.data.models.firebase.FavoriteData, userId: String, onSuccess: (com.zaurh.movieappintern2.data.models.firebase.FavoriteData) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _addToFavoriteState.update { it.copy(alreadyAdded = true) }
            databaseRepository.addToFavorites(movie = movie, userId = userId)
                .collect { result ->
                    Log.d("dsjklajdlsa", "${result.message}")
                    when (result) {
                        is Resource.Error -> {
                            _addToFavoriteState.value =
                                AddToFavoriteState(message = result.message ?: "", alreadyAdded = false)
                        }

                        is Resource.Loading -> {
                            _addToFavoriteState.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            _addToFavoriteState.value = AddToFavoriteState(
                                message = "Added to favorites.",
                                alreadyAdded = true
                            )
                            withContext(Dispatchers.Main) {
                                onSuccess(movie)
                            }
                        }
                    }
                }
        }
    }

    fun removeFromFavorites(movie: com.zaurh.movieappintern2.data.models.firebase.FavoriteData, userId: String, onSuccess: (com.zaurh.movieappintern2.data.models.firebase.FavoriteData) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _addToFavoriteState.update { it.copy(alreadyAdded = false) }
            databaseRepository.removeFromFavorites(movie = movie, userId = userId)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _addToFavoriteState.value =
                                AddToFavoriteState(message = result.message ?: "", alreadyAdded = true)
                        }

                        is Resource.Loading -> {
                            _addToFavoriteState.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            _addToFavoriteState.value = AddToFavoriteState(
                                message = "Removed from favorites.",
                                alreadyAdded = false
                            )
                            withContext(Dispatchers.Main) {
                                onSuccess(movie)
                            }
                        }
                    }
                }
        }
    }




    fun getReviews() {
        clearReviews()
        val movieId = movieDetailsState.value.movieDetails?.id ?: 0
        viewModelScope.launch(Dispatchers.IO) {
            reviewRepository.getReviews(movieId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _reviewState.value = ReviewState(message = result.message ?: "")
                    }

                    is Resource.Loading -> {
                        _reviewState.value = ReviewState(loading = true)
                    }

                    is Resource.Success -> {
                        val reviews = result.data ?: listOf()
                        if (reviews.isEmpty()) {
                            _reviewState.value = ReviewState(message = "No reviews found.")
                            return@collect
                        }
                        _reviewState.value = ReviewState(allReviews = reviews)

                    }
                }
            }
        }
    }

    fun clearReviews() {
        _reviewState.update { it.copy(allReviews = listOf()) }
    }


}