package com.zaurh.movieappintern2.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zaurh.movieappintern2.R
import com.zaurh.movieappintern2.domain.models.Movie

@Composable
fun MovieItemHorizontal(
    movie: Movie,
    onMovieClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie.id) }
            .padding(12.dp)
    ) {
        if (movie.poster.isNotEmpty()) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10)),
                model = movie.poster,
                contentDescription = "",
                placeholder = painterResource(R.drawable.no_image_ic),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(R.drawable.no_image_ic),
                    contentDescription = ""
                )
            }
        }


        Spacer(Modifier.size(8.dp))
        Column {
            var overviewExpanded by remember { mutableStateOf(false) }
            Text(
                text = movie.title,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.size(4.dp))
            Text(
                modifier = Modifier.clickable {
                    overviewExpanded = !overviewExpanded
                },
                text = movie.overview,
                maxLines = if (overviewExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }


    }
}