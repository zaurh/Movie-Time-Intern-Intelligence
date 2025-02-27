package com.zaurh.movieappintern2.presentation.auth.sign_up

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaurh.movieappintern2.navigation.Screen
import com.zaurh.movieappintern2.presentation.auth.sign_up.components.SignUpContent
import com.zaurh.movieappintern2.presentation.auth.sign_up.components.SignUpTopBar
import com.zaurh.movieappintern2.shared.AuthViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { SignUpTopBar(onBackClick = { navController.popBackStack() }) },
        content = { padding ->
            SignUpContent(
                modifier = modifier.padding(padding),
                authViewModel = authViewModel,
                signUpViewModel = signUpViewModel,
                onSuccess = {
                    navController.popBackStack(Screen.SignInScreen.route, inclusive = true)
                },
                onSignInClick = {
                    navController.popBackStack()
                }
            )
        }
    )

}