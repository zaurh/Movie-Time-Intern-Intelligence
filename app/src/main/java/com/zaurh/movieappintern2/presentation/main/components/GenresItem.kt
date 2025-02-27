package com.zaurh.movieappintern2.presentation.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaurh.movieappintern2.data.models.genres.Genre

@Composable
fun GenresItem(
    genre: Genre,
    onGenreClick: (String, Int) -> Unit
) {
    Button(colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.background
    ),modifier = Modifier.padding(start = 8.dp), onClick = {
        onGenreClick(genre.name, genre.id)
    }) {
        Text(text = genre.name, color = MaterialTheme.colorScheme.primary)
    }
}