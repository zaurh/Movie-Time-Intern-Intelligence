package com.zaurh.movieappintern2.presentation.profile.preferences.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaurh.movieappintern2.presentation.profile.preferences.PreferencesViewModel

@Composable
fun PreferencesContent(
    modifier: Modifier = Modifier,
    preferencesViewModel: PreferencesViewModel,
) {
    val switchState = preferencesViewModel.switchState.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DarkModeSwitch(switchState = switchState.value) {
            preferencesViewModel.toggleDarkMode(it)
        }

    }

}

