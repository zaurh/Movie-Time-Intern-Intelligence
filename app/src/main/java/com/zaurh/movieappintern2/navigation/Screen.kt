package com.zaurh.movieappintern2.navigation

sealed class Screen(val route: String) {
    data object DiscoverScreen : Screen("discover_screen/{genreName}/{genreId}")
    data object SignInScreen : Screen("sign_in_screen")
    data object SignUpScreen : Screen("sign_up_screen")
    data object AccountScreen : Screen("account_screen")
    data object PreferencesScreen : Screen("preferences_screen")
    data object ReviewsScreen : Screen("reviews_screen")
    data object HomeScreen: Screen("home_screen")
    data object DetailsScreen : Screen("details_screen/{movieId}")
}