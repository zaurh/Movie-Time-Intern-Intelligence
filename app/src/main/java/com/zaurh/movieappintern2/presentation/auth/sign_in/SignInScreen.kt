package com.zaurh.movieappintern2.presentation.auth.sign_in

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zaurh.movieappintern2.navigation.Screen
import com.zaurh.movieappintern2.presentation.auth.sign_in.components.SignInContent
import com.zaurh.movieappintern2.presentation.auth.sign_in.components.SignInTopBar
import com.zaurh.movieappintern2.shared.AuthViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    Scaffold(
        topBar = { SignInTopBar(onBackClick = { navController.popBackStack() }) },
        content = { padding ->
            SignInContent(
                modifier = modifier.padding(padding),
                authViewModel = authViewModel,
                onSignUpClick = {
                    navController.navigate(Screen.SignUpScreen.route)
                },
                onSuccess = {
                    navController.popBackStack()
                }
            )
        }
    )
}