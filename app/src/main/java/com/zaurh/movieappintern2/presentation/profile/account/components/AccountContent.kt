package com.zaurh.movieappintern2.presentation.profile.account.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zaurh.movieappintern2.presentation.profile.account.AccountViewModel
import com.zaurh.movieappintern2.shared.AuthViewModel

@Composable
fun AccountContent(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel
) {
    val userState = authViewModel.userData.collectAsState()
    val usernameState = accountViewModel.usernameState.collectAsState()

    LaunchedEffect(true) {
        userState.value.data?.username?.let {
            accountViewModel.setUsername(it)
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Text(text = "Username")
            Spacer(Modifier.height(8.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                value = usernameState.value.username,
                onValueChange = {
                    accountViewModel.setUsername(it)
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
        }
        Spacer(Modifier.height(16.dp))
        val loading = usernameState.value.loading

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background
        ),enabled = !loading, onClick = {
            accountViewModel.updateUsername(userState.value.data?.id ?: "") {
                authViewModel.updateUsername(usernameState.value.username)
            }
        }) {
            Text(text = if (loading) "Loading..." else "Update", color = MaterialTheme.colorScheme.primary)
        }
        Spacer(Modifier.height(16.dp))
        AnimatedVisibility(
            visible = usernameState.value.message.isNotEmpty()
        ) {
            Text(text = usernameState.value.message)
        }

    }
}