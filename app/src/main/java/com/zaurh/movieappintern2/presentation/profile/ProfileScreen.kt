package com.zaurh.movieappintern2.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaurh.movieappintern2.navigation.Screen
import com.zaurh.movieappintern2.presentation.profile.components.ProfileItem
import com.zaurh.movieappintern2.shared.AuthViewModel
import com.zaurh.movieappintern2.util.Constants.NEED_SIGN_IN_MESSAGE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel
) {
    val isSignedIn = authViewModel.isSignedIn.collectAsState()

    val userDataState = authViewModel.userData.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") }
            )
        },
        content = { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileItem(title = "Account", icon = Icons.Default.Person) {
                    if (userDataState.value.data != null) {
                        navController.navigate(Screen.AccountScreen.route)
                    } else {
                        Toast.makeText(context, NEED_SIGN_IN_MESSAGE, Toast.LENGTH_SHORT).show()
                    }
                }
                ProfileItem(title = "Preferences", icon = Icons.Default.Settings) {
                    navController.navigate(Screen.PreferencesScreen.route)
                }
                ProfileItem(title = "My reviews", icon = Icons.Default.ThumbUp) {
                    if (userDataState.value.data != null) {
                        navController.navigate(Screen.ReviewsScreen.route)
                    } else {
                        Toast.makeText(context, NEED_SIGN_IN_MESSAGE, Toast.LENGTH_SHORT).show()
                    }
                }
                Spacer(Modifier.size(24.dp))


                if(!isSignedIn.value){
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),onClick = {
                        navController.navigate(Screen.SignInScreen.route)
                    }) {
                        Text(text = "Sign in", color = MaterialTheme.colorScheme.primary)
                    }
                }else{
                    Text(
                        text = "Sign out",
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.clickable {
                            authViewModel.logout()
                        }
                    )
                }

            }

        }
    )
}