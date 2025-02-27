package com.zaurh.movieappintern2.presentation.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zaurh.movieappintern2.domain.models.Movie

@Composable
fun RecommendedMoviesItem(
    movie: Movie,
    onMovieClick: (movieId: Int) -> Unit
) {
    AsyncImage(
        modifier = Modifier
            .height(200.dp)
            .width(120.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(5))
            .clickable {
                onMovieClick(movie.id)
            },
        model = movie.poster,
        contentDescription = "",
        contentScale = ContentScale.Crop
    )

}
