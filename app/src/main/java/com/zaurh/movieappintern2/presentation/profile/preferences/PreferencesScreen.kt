package com.zaurh.movieappintern2.presentation.profile.preferences

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaurh.movieappintern2.presentation.profile.preferences.components.PreferencesContent
import com.zaurh.movieappintern2.presentation.profile.preferences.components.PreferencesTopBar

@Composable
fun PreferencesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    preferencesViewModel: PreferencesViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { PreferencesTopBar(onBackClick = { navController.popBackStack() }) },
        content = { padding ->
            PreferencesContent(
                modifier = modifier.padding(padding),
                preferencesViewModel = preferencesViewModel
            )
        }
    )
}