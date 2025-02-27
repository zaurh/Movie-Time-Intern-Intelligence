package com.zaurh.movieappintern2.presentation.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.core.View
import com.zaurh.movieappintern2.data.toMovie
import com.zaurh.movieappintern2.domain.repository.MovieRepository
import com.zaurh.movieappintern2.presentation.discover.states.DiscoverState
import com.zaurh.movieappintern2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _discoverState = MutableStateFlow(DiscoverState())
    val discoverState = _discoverState.asStateFlow()

    fun discoverMovies(genreId: Int, page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.discoverMovies(genreId, page).collect { result ->
                when(result){
                    is Resource.Error -> {
                        _discoverState.value = _discoverState.value.copy(error = result.message ?: "")
                    }
                    is Resource.Loading -> {
                        _discoverState.value = _discoverState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val movies = result.data?.results?.map { it.toMovie() } ?: emptyList()
                        _discoverState.value = _discoverState.value.copy(
                            page = result.data?.page ?: 0,
                            totalPages = result.data?.total_pages ?: 0,
                            totalResults = result.data?.total_results ?: 0,
                            movies = movies
                        )
                    }
                }
            }
        }
    }

    fun changePage(genreId: Int,page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            discoverMovies(genreId, page)
        }
    }
}