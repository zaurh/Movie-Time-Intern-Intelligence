package com.zaurh.movieappintern2.presentation.discover.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaurh.movieappintern2.presentation.discover.DiscoverViewModel
import com.zaurh.movieappintern2.shared.MovieItemHorizontal
import com.zaurh.movieappintern2.shared.PageSwitcher

@Composable
fun DiscoverContent(
    genreId: Int,
    paddingValues: PaddingValues,
    onMovieClick: (Int) -> Unit,
    discoverViewModel: DiscoverViewModel
) {
    val discoverState = discoverViewModel.discoverState.collectAsState()

    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingValues)
    ) {
        LazyColumn {
            val discoverMovies = discoverState.value.movies
            item {
                PageSwitcher(
                    currentPage = discoverState.value.page,
                    totalPage = discoverState.value.totalPages,
                    onPreviousClick = {
                        discoverViewModel.changePage(
                            genreId = genreId,
                            page = discoverState.value.page - 1
                        )
                    },
                    onNextClick = {
                        discoverViewModel.changePage(
                            genreId = genreId,
                            page = discoverState.value.page + 1
                        )
                    }
                )
            }
            items(discoverMovies) {
                MovieItemHorizontal(it) { movieId ->
                    onMovieClick(movieId)
                }

            }
        }
    }
}

