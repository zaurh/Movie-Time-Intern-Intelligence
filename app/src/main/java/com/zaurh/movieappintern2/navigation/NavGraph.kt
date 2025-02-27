package com.zaurh.movieappintern2.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zaurh.movieappintern2.presentation.auth.sign_in.SignInScreen
import com.zaurh.movieappintern2.presentation.auth.sign_up.SignUpScreen
import com.zaurh.movieappintern2.presentation.details.DetailScreen
import com.zaurh.movieappintern2.presentation.discover.DiscoverScreen
import com.zaurh.movieappintern2.presentation.home.HomeScreen
import com.zaurh.movieappintern2.presentation.profile.account.AccountScreen
import com.zaurh.movieappintern2.presentation.profile.preferences.PreferencesScreen
import com.zaurh.movieappintern2.presentation.profile.reviews.ReviewsScreen
import com.zaurh.movieappintern2.shared.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    val authViewModel = hiltViewModel<AuthViewModel>()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Screen.HomeScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it })
            }
        ) {
            HomeScreen(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screen.AccountScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            }
        ) {
            AccountScreen(
                modifier = modifier,
                authViewModel = authViewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.DiscoverScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            },
            arguments = listOf(
                navArgument("genreId") { type = NavType.IntType },
                navArgument("genreName") { type = NavType.StringType }
            )) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val genreId = arguments.getInt("genreId")
            val genreName = arguments.getString("genreName") ?: "Discover"

            DiscoverScreen(
                genreName = genreName,
                genreId = genreId,
                navController = navController
            )
        }

        composable(
            route = Screen.PreferencesScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            }
        ) {
            PreferencesScreen(modifier = modifier, navController = navController)
        }

        composable(
            route = Screen.ReviewsScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            }
        ) {
            ReviewsScreen(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screen.DetailsScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            }
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val movieId = arguments.getInt("movieId")
            DetailScreen(
                modifier = modifier,
                navController = navController,
                movieId = movieId,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screen.SignInScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }) // <--
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it }) // -->
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) // <--
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it }) // -->
            }
        ) {
            SignInScreen(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(
            route = Screen.SignUpScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            }
        ) {
            SignUpScreen(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

    }
}