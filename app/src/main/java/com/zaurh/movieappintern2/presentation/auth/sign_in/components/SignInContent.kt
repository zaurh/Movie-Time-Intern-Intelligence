package com.zaurh.movieappintern2.presentation.auth.sign_in.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zaurh.movieappintern2.presentation.auth.components.AuthTextField
import com.zaurh.movieappintern2.presentation.auth.sign_in.SignInViewModel
import com.zaurh.movieappintern2.shared.AuthViewModel

@Composable
fun SignInContent(
    modifier: Modifier,
    signInViewModel: SignInViewModel = hiltViewModel(),
    authViewModel: AuthViewModel,
    onSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val emailState = signInViewModel.emailState.collectAsState()
    val passwordState = signInViewModel.passwordState.collectAsState()
    val signInState = authViewModel.signInState.collectAsState()

    Column(
        modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface).imePadding(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().weight(8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AuthTextField(
                value = emailState.value,
                onValueChange = { signInViewModel.onEmailChange(it) },
                label = "Email",
                leadingIcon = Icons.Default.Email,
                visualTransformation = VisualTransformation.None
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthTextField(
                value = passwordState.value,
                onValueChange = { signInViewModel.onPasswordChange(it) },
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                visualTransformation = PasswordVisualTransformation()
            )



            Spacer(Modifier.height(16.dp))

            val loading = signInState.value.loading

            Button(colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            ),enabled = !loading, onClick = {
                authViewModel.signIn(emailState.value, passwordState.value) {
                    onSuccess()
                }
            }) {
                Text(text = if (loading) "Loading..." else "Sign In", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.height(12.dp))
            AnimatedVisibility(signInState.value.error != null) {
                Text(
                    text = signInState.value.error ?: "",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize().weight(2f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Don't have an account?", color = MaterialTheme.colorScheme.primary)
            Text(
                text = "Sign up",
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onSignUpClick()
                }
            )
        }

    }
}