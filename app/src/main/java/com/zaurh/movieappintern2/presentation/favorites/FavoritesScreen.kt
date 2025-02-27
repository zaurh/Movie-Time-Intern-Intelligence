package com.zaurh.movieappintern2.presentation.favorites

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.zaurh.movieappintern2.navigation.Screen
import com.zaurh.movieappintern2.presentation.favorites.components.FavoritesContent
import com.zaurh.movieappintern2.shared.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(title = { Text(text = "Favorites") })
        },
        content = { padding ->
            FavoritesContent(
                authViewModel = authViewModel,
                padding = padding,
                onSignInClick = {
                    navController.navigate(Screen.SignInScreen.route)
                },
                onMovieClick = {
                    navController.navigate("details_screen/$it")
                })
        }
    )

}