package com.zaurh.movieappintern2.presentation.profile.reviews

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaurh.movieappintern2.shared.AuthViewModel
import com.zaurh.movieappintern2.presentation.profile.reviews.components.ReviewsContent
import com.zaurh.movieappintern2.presentation.profile.reviews.components.ReviewsTopBar

@Composable
fun ReviewsScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    reviewViewModel: ReviewsViewModel = hiltViewModel(),
    navController: NavController
) {

    Scaffold(
        topBar = {
            ReviewsTopBar(onBackClick = {
                navController.popBackStack()
            })
        },
        content = { padding ->
            ReviewsContent(
                modifier = modifier.padding(padding),
                authViewModel = authViewModel,
                reviewsViewModel = reviewViewModel,
                onReviewClick = {
                    navController.navigate("details_screen/$it")
                }
            )
        }
    )
}