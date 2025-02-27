package com.zaurh.movieappintern2.presentation.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zaurh.movieappintern2.presentation.search.components.SearchContent
import com.zaurh.movieappintern2.presentation.search.components.SearchTopBar

@Composable
fun SearchScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { SearchTopBar()},
        content = { padding ->
            SearchContent(padding = padding, navController = navController)
        }
    )
}