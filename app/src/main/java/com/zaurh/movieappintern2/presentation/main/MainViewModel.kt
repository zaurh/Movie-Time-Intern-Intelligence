package com.zaurh.movieappintern2.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaurh.movieappintern2.domain.repository.MovieRepository
import com.zaurh.movieappintern2.presentation.main.states.GenresState
import com.zaurh.movieappintern2.presentation.main.states.NowPlayingState
import com.zaurh.movieappintern2.presentation.main.states.PopularMoviesState
import com.zaurh.movieappintern2.presentation.main.states.TopRatedState
import com.zaurh.movieappintern2.presentation.main.states.UpcomingMoviesState
import com.zaurh.movieappintern2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _popularMoviesState = MutableStateFlow(PopularMoviesState())
    val popularMoviesState = _popularMoviesState.asStateFlow()

    private val _upcomingMoviesState = MutableStateFlow(UpcomingMoviesState())
    val upcomingMoviesState = _upcomingMoviesState.asStateFlow()

    private val _nowPlayingMoviesState = MutableStateFlow(NowPlayingState())
    val nowPlayingMoviesState = _nowPlayingMoviesState.asStateFlow()

    private val _topRatedMoviesState = MutableStateFlow(TopRatedState())
    val topRatedMoviesState = _topRatedMoviesState.asStateFlow()

    private val _genresState = MutableStateFlow(GenresState())
    val genresState = _genresState.asStateFlow()


    init {
        getPopularMovies()
        getUpcomingMovies()
        getGenres()
        getNowPlayingMovies()
        getTopRatedMovies()
    }

    private fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getGenres().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _genresState.update { it.copy(error = result.message ?: "") }
                    }

                    is Resource.Loading -> {
                        _genresState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _genresState.update { it.copy(genresDto = result.data) }
                    }
                }
            }
        }

    }

    private fun getUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getUpcomingMovies().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _upcomingMoviesState.update { it.copy(error = result.message ?: "") }
                    }

                    is Resource.Loading -> {
                        _upcomingMoviesState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _upcomingMoviesState.update { it.copy(movies = result.data ?: emptyList()) }
                    }
                }
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovies().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _popularMoviesState.update { it.copy(error = result.message ?: "") }
                    }

                    is Resource.Loading -> {
                        _popularMoviesState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _popularMoviesState.update { it.copy(movies = result.data ?: emptyList()) }
                    }
                }
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getNowPlayingMovies().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _nowPlayingMoviesState.update { it.copy(error = result.message ?: "") }
                    }

                    is Resource.Loading -> {
                        _nowPlayingMoviesState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _nowPlayingMoviesState.update { it.copy(movies = result.data ?: emptyList()) }
                    }
                }
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getTopRatedMovies().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _topRatedMoviesState.update { it.copy(error = result.message ?: "") }
                    }

                    is Resource.Loading -> {
                        _topRatedMoviesState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _topRatedMoviesState.update { it.copy(movies = result.data ?: emptyList()) }
                    }
                }
            }
        }
    }
}