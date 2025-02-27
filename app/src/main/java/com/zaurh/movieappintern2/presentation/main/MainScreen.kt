package com.zaurh.movieappintern2.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.zaurh.movieappintern2.presentation.main.components.GenresItem
import com.zaurh.movieappintern2.shared.MovieItem
import com.zaurh.movieappintern2.presentation.main.components.UpcomingMovieItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val popularMoviesState = mainViewModel.popularMoviesState.collectAsState()
    val upcomingMoviesState = mainViewModel.upcomingMoviesState.collectAsState()
    val nowPlayingMoviesState = mainViewModel.nowPlayingMoviesState.collectAsState()
    val topRatedMoviesState = mainViewModel.topRatedMoviesState.collectAsState()

    val genresState = mainViewModel.genresState.collectAsState()

    val pagerState =
        rememberPagerState(
            initialPage = 0,
            pageCount = { upcomingMoviesState.value.movies.size })

    var scrollJob: Job? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        scrollJob?.cancel()
        scrollJob = scope.launch {
            while (isActive) {
                delay(10000)
                val nextPage = if (pagerState.canScrollForward) {
                    pagerState.currentPage + 1
                } else {
                    0
                }
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_PAUSE) {
        scrollJob?.cancel()
    }


    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.Start
    ) {
        Box(contentAlignment = Alignment.Center) {
            HorizontalPager(
                modifier = Modifier
                    .width(400.dp)
                    .height((screenHeight / 2).dp),
                state = pagerState,
            ) { page ->
                val movie = upcomingMoviesState.value.movies.getOrNull(page)
                movie?.let {
                    UpcomingMovieItem(it) {
                        navController.navigate("details_screen/$it")
                    }
                }
            }
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(color, CircleShape)
                            .size(5.dp)
                    )
                }
            }
        }


        Text(
            text = "Categories",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(12.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow {
            val genres = genresState.value.genresDto?.genres ?: listOf()

            items(genres) {
                GenresItem(
                    genre = it,
                    onGenreClick = { genreName, genreId ->
                        navController.navigate("discover_screen/${genreName}/$genreId")
                    }
                )
            }
        }

        Text(
            text = "Most popular",
            modifier = Modifier.padding(12.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow {
            items(popularMoviesState.value.movies) {
                MovieItem(it) {
                    navController.navigate("details_screen/$it")
                }
            }
        }

        Text(
            text = "Now playing",
            modifier = Modifier.padding(12.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow {
            items(nowPlayingMoviesState.value.movies) {
                MovieItem(it) {
                    navController.navigate("details_screen/$it")
                }
            }
        }

        Text(
            text = "Top rated",
            modifier = Modifier.padding(12.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow {
            items(topRatedMoviesState.value.movies) {
                MovieItem(it) {
                    navController.navigate("details_screen/$it")
                }
            }
        }


    }

}




