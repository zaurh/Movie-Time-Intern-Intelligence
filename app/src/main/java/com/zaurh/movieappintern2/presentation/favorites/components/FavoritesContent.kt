package com.zaurh.movieappintern2.presentation.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zaurh.movieappintern2.domain.models.Movie
import com.zaurh.movieappintern2.shared.AuthViewModel
import com.zaurh.movieappintern2.shared.MovieItem

@Composable
fun FavoritesContent(
    padding: PaddingValues,
    onMovieClick: (movieId: Int) -> Unit,
    onSignInClick: () -> Unit,
    authViewModel: AuthViewModel
) {
    val userData = authViewModel.userData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = padding.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val favoriteMovies = userData.value.data?.favoriteMovies?.reversed() ?: listOf()
        if (userData.value.data != null) {
            if (favoriteMovies.isEmpty()) {
                Text(
                    text = "You have no favorite movies.",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                items(favoriteMovies) { movie ->
                    MovieItem(
                        movieData = Movie(
                            id = movie.movieId,
                            poster = movie.poster
                        )
                    ) {
                        onMovieClick(movie.movieId)
                    }
                }
            }
        } else {
            Text(
                text = "You need to sign in to see your favorite movies.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(12.dp))
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            ), onClick = {
                onSignInClick()
            }) {
                Text(text = "Sign in", color = MaterialTheme.colorScheme.primary)
            }
        }

    }
}