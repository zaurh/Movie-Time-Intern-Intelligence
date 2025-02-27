package com.zaurh.movieappintern2.presentation.details.pager_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaurh.movieappintern2.presentation.details.DetailScreenViewModel
import com.zaurh.movieappintern2.presentation.details.components.YouTubeVideo

@Composable
fun Trailer(
    detailScreenViewModel: DetailScreenViewModel
) {
    val movieVideoState = detailScreenViewModel.movieVideoState.collectAsState()

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        if(movieVideoState.value.isLoading){
            CircularProgressIndicator()
        }
        else if (movieVideoState.value.message.isNotEmpty()){
            Text(text = movieVideoState.value.message)
        }
        movieVideoState.value.movieVideos.firstOrNull()?.let {
            YouTubeVideo(
                videoKey = it.key
            )
        }
    }

}