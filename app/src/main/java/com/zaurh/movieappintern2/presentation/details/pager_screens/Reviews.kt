package com.zaurh.movieappintern2.presentation.details.pager_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zaurh.movieappintern2.navigation.Screen
import com.zaurh.movieappintern2.presentation.details.DetailScreenViewModel
import com.zaurh.movieappintern2.presentation.details.components.ReviewItem
import com.zaurh.movieappintern2.shared.AuthViewModel

@Composable
fun Reviews(
    detailScreenViewModel: DetailScreenViewModel,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val reviewState = detailScreenViewModel.reviewState.collectAsState()
    val userData = authViewModel.userData.collectAsState()

    LaunchedEffect(true) {
        detailScreenViewModel.getReviews()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val allReviews =
            reviewState.value.allReviews.sortedByDescending { it.timestamp }

        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Reviews (${allReviews.count()})", color = MaterialTheme.colorScheme.primary)
                if (userData.value.data != null) {
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ), onClick = {
                        detailScreenViewModel.onAlertShow()
                    }) {
                        Text("Add review", color = MaterialTheme.colorScheme.primary)
                    }
                } else {
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ), onClick = {
                        navController.navigate(Screen.SignInScreen.route)
                    }) {
                        Text("Sign in to review", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
            if (reviewState.value.loading) {
                CircularProgressIndicator()
            } else if (reviewState.value.message.isNotEmpty()) {
                Text(
                    text = reviewState.value.message,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        items(allReviews) {
            ReviewItem(it)
        }
    }

}