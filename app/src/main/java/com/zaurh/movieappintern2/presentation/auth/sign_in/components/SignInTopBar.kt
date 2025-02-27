package com.zaurh.movieappintern2.presentation.auth.sign_in.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Sign in") },
        navigationIcon = {
            IconButton(onClick = {
                onBackClick()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
            }
        }
    )
}