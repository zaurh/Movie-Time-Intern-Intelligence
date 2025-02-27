package com.zaurh.movieappintern2.presentation.profile.reviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaurh.movieappintern2.shared.AuthViewModel
import com.zaurh.movieappintern2.presentation.profile.reviews.ReviewsViewModel

@Composable
fun ReviewsContent(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    reviewsViewModel: ReviewsViewModel,
    onReviewClick: (Int) -> Unit
) {
    val userData = authViewModel.userData.collectAsState()
    val myReviewsState = reviewsViewModel.myReviewsState.collectAsState()

    LaunchedEffect(true) {
        userData.value.data?.id?.let {
            reviewsViewModel.getMyReviews(it)
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (myReviewsState.value.isLoading) {
            CircularProgressIndicator()
        } else if (myReviewsState.value.message.isNotEmpty()) {
            Text(text = myReviewsState.value.message, modifier = Modifier.padding(12.dp), color = MaterialTheme.colorScheme.primary)
        }
        LazyColumn {
            val reviews = myReviewsState.value.reviews.sortedByDescending { it.timestamp }
            items(reviews) {
                ReviewsItem(it) { id ->
                    onReviewClick(id)
                }
            }
        }
    }
}