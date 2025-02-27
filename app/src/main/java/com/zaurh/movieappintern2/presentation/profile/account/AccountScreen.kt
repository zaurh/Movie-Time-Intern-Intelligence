package com.zaurh.movieappintern2.presentation.profile.account

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaurh.movieappintern2.shared.AuthViewModel
import com.zaurh.movieappintern2.presentation.profile.account.components.AccountContent
import com.zaurh.movieappintern2.presentation.profile.account.components.AccountTopBar

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = { AccountTopBar(onBackClick = { navController.popBackStack() }) },
        content = { padding ->
            AccountContent(
                modifier = modifier.padding(padding),
                authViewModel = authViewModel,
                accountViewModel = accountViewModel
            )
        }
    )
}