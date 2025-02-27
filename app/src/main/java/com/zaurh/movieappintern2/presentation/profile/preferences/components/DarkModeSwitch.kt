package com.zaurh.movieappintern2.presentation.profile.preferences.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DarkModeSwitch(
    switchState: Boolean,
    onSwitchChange: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth().clickable {
            onSwitchChange(!switchState)
        }.padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Dark mode", color = MaterialTheme.colorScheme.primary)
        Switch(checked = switchState, onCheckedChange = {
            onSwitchChange(it)
        })
    }
}