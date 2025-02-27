package com.zaurh.movieappintern2.presentation.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.zaurh.movieappintern2.domain.models.Movie

@Composable
fun UpcomingMovieItem(
    movieData: Movie,
    onMovieClick: (movieId: Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().clickable {
        onMovieClick(movieData.id)
    }) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = movieData.poster,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}