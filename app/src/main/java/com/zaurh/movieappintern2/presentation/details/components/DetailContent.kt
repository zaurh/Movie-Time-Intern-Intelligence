package com.zaurh.movieappintern2.presentation.details.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.zaurh.movieappintern2.R
import com.zaurh.movieappintern2.data.toFavoriteData
import com.zaurh.movieappintern2.presentation.details.DetailScreenViewModel
import com.zaurh.movieappintern2.presentation.details.pager_screens.Reviews
import com.zaurh.movieappintern2.presentation.details.pager_screens.Similar
import com.zaurh.movieappintern2.presentation.details.pager_screens.Trailer
import com.zaurh.movieappintern2.shared.AuthViewModel
import com.zaurh.movieappintern2.util.Constants.NEED_SIGN_IN_MESSAGE
import kotlinx.coroutines.launch


@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    detailScreenViewModel: DetailScreenViewModel,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val movieDetailsState = detailScreenViewModel.movieDetailsState.collectAsState()
    val addToFavoriteState = detailScreenViewModel.addToFavoriteState.collectAsState()
    val reviewState = detailScreenViewModel.reviewState.collectAsState()
    val userData = authViewModel.userData.collectAsState()
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val scope = rememberCoroutineScope()

    val unselectedColor = MaterialTheme.colorScheme.primary.copy(0.5f)

    val pagerList = listOf(
        PagerData(
            title = "Similar",
            indicator = 0,
            unselectedColor = unselectedColor
        ),
        PagerData(
            title = "Trailer",
            indicator = 1,
            unselectedColor = unselectedColor

        ),
        PagerData(
            title = "About",
            indicator = 2,
            unselectedColor = unselectedColor

        ),
        PagerData(
            title = "Reviews",
            indicator = 3,
            unselectedColor = unselectedColor

        )
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pagerList.size })

    if (reviewState.value.alertVisible) {
        RateAlert(
            rateValue = reviewState.value.opinion,
            onRateValueChange = detailScreenViewModel::onRateValueChange,
            onDismiss = detailScreenViewModel::onAlertDismiss,
            rate = reviewState.value.rate,
            onSend = {
                userData.value.data?.let {
                    detailScreenViewModel.addReview(it)
                }
                detailScreenViewModel.onAlertDismiss()
            },
            onThumbClick = {
                detailScreenViewModel.selectThumb(it)
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (movieDetailsState.value.message.isNotEmpty()) {
            Text(text = movieDetailsState.value.message, color = MaterialTheme.colorScheme.error)
        }
        movieDetailsState.value.movieDetails?.let { movie ->
            Box(contentAlignment = Alignment.Center) {
                AsyncImage(
                    modifier = modifier
                        .width(400.dp)
                        .height((screenHeight / 2).dp),
                    model = movie.poster,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height((screenHeight / 10).dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                )
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 32.dp, start = 32.dp),
                    onClick = {
                        navController.popBackStack()
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    modifier = modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 32.dp, end = 32.dp),
                    onClick = {
                        if (userData.value.data == null){
                            Toast.makeText(context, NEED_SIGN_IN_MESSAGE, Toast.LENGTH_SHORT).show()
                        }
                        val userId = userData.value.data?.id ?: ""

                        if (addToFavoriteState.value.alreadyAdded) {
                            detailScreenViewModel.removeFromFavorites(
                                movie = movie.toFavoriteData(),
                                userId = userId,
                                onSuccess = {
                                    authViewModel.removeFromFavorite(
                                        movie.toFavoriteData()
                                    )
                                })
                        } else {
                            detailScreenViewModel.addToFavorites(
                                movie = movie.toFavoriteData(),
                                userId = userId,
                                onSuccess = {
                                    authViewModel.addToFavorite(
                                        movie.toFavoriteData()
                                    )
                                }
                            )

                        }
                    }) {
                    Icon(
                        imageVector = if (addToFavoriteState.value.alreadyAdded) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "",
                        tint = if (addToFavoriteState.value.alreadyAdded) Color.Red else Color.White
                    )
                }
                if (movieDetailsState.value.isLoading) {
                    CircularProgressIndicator()
                }

            }

            Text(
                text = movie.title,
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(12.dp))

            val releaseDate = movie.releaseDate.take(4)
            val genres = movie.genres.map { it.name }.joinToString(", ")
            val time = movie.time
            Text(
                text = "$releaseDate | $time",
                color = MaterialTheme.colorScheme.primary.copy(0.5f)
            )
            Text(
                text = genres,
                color = MaterialTheme.colorScheme.primary.copy(0.5f),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(32.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                pagerList.forEach {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            modifier = Modifier.clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(it.indicator)
                                }
                            },
                            text = it.title,
                            fontSize = 18.sp,
                            color = if (pagerState.currentPage == it.indicator) it.selectedColor else it.unselectedColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(
                            modifier = Modifier.width(64.dp),
                            color = if (pagerState.currentPage == it.indicator) it.selectedColor else Color.Transparent
                        )


                    }
                }
            }
            Spacer(Modifier.size(12.dp))
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { index ->
                when (index) {
                    0 -> {
                        Similar(detailScreenViewModel = detailScreenViewModel)
                    }

                    1 -> {
                        Trailer(detailScreenViewModel = detailScreenViewModel)
                    }

                    2 -> {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                text = movie.overview.ifEmpty { "No description found." },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    3 -> {
                        Reviews(
                            detailScreenViewModel = detailScreenViewModel,
                            authViewModel = authViewModel,
                            navController = navController
                        )
                    }
                }


            }


        }

    }
}

data class PagerData(
    val title: String,
    val indicator: Int,
    val selectedColor: Color = Color.Red,
    val unselectedColor: Color
)


