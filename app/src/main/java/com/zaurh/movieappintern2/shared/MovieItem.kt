package com.zaurh.movieappintern2.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
fun MovieItem(
    movieData: Movie,
    onMovieClick: (movieId: Int) -> Unit
) {
    Box(modifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(10))
        .clickable {
            onMovieClick(movieData.id)
        }) {
        AsyncImage(
            modifier = Modifier.height(150.dp).width(100.dp),
            model = movieData.poster,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}
