package com.zaurh.movieappintern2.presentation.search.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaurh.movieappintern2.presentation.search.SearchViewModel
import com.zaurh.movieappintern2.shared.SearchBar
import com.zaurh.movieappintern2.shared.MovieItemHorizontal
import com.zaurh.movieappintern2.shared.PageSwitcher

@Composable
fun SearchContent(
    padding: PaddingValues,
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val searchMovieState = searchViewModel.searchMovieState.collectAsState()
    val searchQuery = searchViewModel.searchQuery.collectAsState()

    BackHandler(
        onBack = {
            if (searchQuery.value.isNotEmpty()) {
                searchViewModel.setSearchQuery("")
            } else {
                navController.popBackStack()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = padding.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            query = searchQuery.value,
            onQueryChange = searchViewModel::setSearchQuery,
            label = "Search a movie..."
        )
        if (searchMovieState.value.message.isNotEmpty()) {
            Text(
                text = searchMovieState.value.message,
                modifier = Modifier.padding(12.dp)
            )
        }
        if (searchMovieState.value.loading) {
            CircularProgressIndicator(modifier = Modifier.padding(12.dp))
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(12.dp), horizontalAlignment = Alignment.Start
        ) {
            val searchedMovies = searchMovieState.value.searchedMovies

            if (searchedMovies.isNotEmpty()) {
                item {
                    PageSwitcher(
                        currentPage = searchMovieState.value.page,
                        totalPage = searchMovieState.value.totalPages,
                        onPreviousClick = {
                            searchViewModel.changePage(page = searchMovieState.value.page - 1)
                        },
                        onNextClick = {
                            searchViewModel.changePage(page = searchMovieState.value.page + 1)
                        }
                    )
                }
            }
            items(searchedMovies) {
                MovieItemHorizontal(movie = it, onMovieClick = {
                    navController.navigate("details_screen/$it")
                })
            }

        }
    }
}