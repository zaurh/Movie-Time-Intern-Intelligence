package com.zaurh.movieappintern2.presentation.details

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaurh.movieappintern2.data.models.firebase.FavoriteData
import com.zaurh.movieappintern2.presentation.details.components.DetailContent
import com.zaurh.movieappintern2.shared.AuthViewModel

@Composable
fun DetailScreen(
    modifier: Modifier,
    movieId: Int,
    navController: NavController,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel(),
    authViewModel: AuthViewModel
) {
    val userData = authViewModel.userData.collectAsState()
    val movieDetailsState = detailScreenViewModel.movieDetailsState.collectAsState()


    LaunchedEffect(true) {
        if (movieDetailsState.value.movieId != 0) {
            detailScreenViewModel.getMovieDetails(movieDetailsState.value.movieId)
        } else {
            detailScreenViewModel.getMovieDetails(movieId)
        }
    }
    LaunchedEffect(movieDetailsState.value.movieId) {
        val movie = movieDetailsState.value.movieDetails
        userData.value.data?.let {
            detailScreenViewModel.checkIfAlreadyAdded(
                movie = com.zaurh.movieappintern2.data.models.firebase.FavoriteData(
                    movieId = movie?.id ?: 0, poster = movie?.poster ?: ""
                ),
                userData = it
            )
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        content = {
            DetailContent(
                modifier = modifier.padding(it),
                detailScreenViewModel = detailScreenViewModel,
                authViewModel = authViewModel,
                navController = navController
            )
        }
    )


}