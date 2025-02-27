package com.zaurh.movieappintern2.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaurh.movieappintern2.data.toMovie
import com.zaurh.movieappintern2.domain.models.Movie
import com.zaurh.movieappintern2.domain.repository.MovieRepository
import com.zaurh.movieappintern2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchMovieState(
    val loading: Boolean = false,
    val page: Int = 1,
    val totalPages: Int = 0,
    val totalResults: Int = 0,
    val searchedMovies: List<Movie> = emptyList(),
    val message: String = ""
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _searchMovieState = MutableStateFlow(SearchMovieState())
    val searchMovieState = _searchMovieState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    fun setSearchQuery(query: String) {
        searchJob?.cancel()
        _searchQuery.value = query

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotEmpty()) {
                delay(1000)
                searchMovie(query)
            }
        }
    }

    fun changePage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            searchMovie(searchQuery.value, page)
        }
    }

    private suspend fun searchMovie(query: String, page: Int = 1) {
        repository.searchMovies(query = query, page = page).collect { result ->
            when (result) {
                is Resource.Error -> {
                    _searchMovieState.update {
                        it.copy(
                            message = result.message ?: "",
                            loading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _searchMovieState.value = SearchMovieState(loading = true)
                }

                is Resource.Success -> {
                    val searchedMovies = result.data?.results?.map { it.toMovie() } ?: listOf()
                    if (searchedMovies.isEmpty()) {
                        _searchMovieState.value = SearchMovieState(message = "No movies found.")
                        return@collect
                    }
                    _searchMovieState.value = SearchMovieState(
                        page = result.data?.page ?: 0,
                        totalPages = result.data?.total_pages ?: 0,
                        totalResults = result.data?.total_results ?: 0,
                        searchedMovies = searchedMovies
                    )
                }
            }

        }

    }
}