package com.zaurh.movieappintern2.presentation.details.pager_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaurh.movieappintern2.presentation.details.DetailScreenViewModel
import com.zaurh.movieappintern2.presentation.details.components.RecommendedMoviesItem

@Composable
fun Similar(
    detailScreenViewModel: DetailScreenViewModel
) {
    val recommendedMoviesState = detailScreenViewModel.recommendedMoviesState.collectAsState()

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        if(recommendedMoviesState.value.isLoading){
            CircularProgressIndicator()
        }
        else if (recommendedMoviesState.value.message.isNotEmpty()){
            Text(text = recommendedMoviesState.value.message)
        }
        LazyRow {
            val recommendedMovies = recommendedMoviesState.value.recommendedMovies
            items(recommendedMovies) {
                RecommendedMoviesItem(
                    movie = it,
                    onMovieClick = detailScreenViewModel::getMovieDetails
                )
            }
        }
    }

}