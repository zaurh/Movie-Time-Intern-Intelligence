package com.zaurh.movieappintern2.presentation.discover

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaurh.movieappintern2.presentation.discover.components.DiscoverContent
import com.zaurh.movieappintern2.presentation.discover.components.DiscoverTopBar

@Composable
fun DiscoverScreen(
    genreName: String,
    genreId: Int,
    navController: NavController,
    discoverViewModel: DiscoverViewModel = hiltViewModel()
) {
    val discoverState = discoverViewModel.discoverState.collectAsState()

    LaunchedEffect(true) {
        if (discoverState.value.movies.isEmpty()){
            discoverViewModel.discoverMovies(genreId)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            DiscoverTopBar(title = genreName,onBackClick = {
                navController.popBackStack()
            })
        },
        content = { padding ->
            DiscoverContent(
                genreId = genreId,
                paddingValues = padding,
                discoverViewModel = discoverViewModel,
                onMovieClick = {
                    navController.navigate("details_screen/$it")
                }
            )
        }
    )
}