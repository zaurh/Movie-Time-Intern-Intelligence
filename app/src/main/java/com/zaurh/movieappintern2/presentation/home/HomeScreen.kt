package com.zaurh.movieappintern2.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zaurh.movieappintern2.presentation.favorites.FavoritesScreen
import com.zaurh.movieappintern2.presentation.main.MainScreen
import com.zaurh.movieappintern2.presentation.profile.ProfileScreen
import com.zaurh.movieappintern2.presentation.search.SearchScreen
import com.zaurh.movieappintern2.shared.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController
) {

    val scope = rememberCoroutineScope()

    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    val tabItems = listOf(
        TabItem(
            index = 0,
            title = "Home",
            icon = Icons.Default.Home,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor
        ),
        TabItem(
            index = 1,
            title = "Search",
            icon = Icons.Default.Search,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor
        ),
        TabItem(
            index = 2,
            title = "Favorites",
            icon = Icons.Default.Favorite,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor
        ),
        TabItem(
            index = 3,
            title = "Profile",
            icon = Icons.Default.Person,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor
        )
    )

    val pagerState = rememberPagerState(initialPage = 0) { tabItems.size }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        content = { padding ->
            HorizontalPager(
                state = pagerState,
                modifier = modifier.padding(padding),
                pageContent = { index ->
                    when (index) {
                        0 -> MainScreen(
                            modifier = modifier,
                            navController = navController
                        )

                        1 -> SearchScreen(navController = navController)
                        2 -> FavoritesScreen(authViewModel = authViewModel, navController = navController)
                        3 -> ProfileScreen(authViewModel = authViewModel, navController = navController)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.background
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    tabItems.forEach { item ->
                        Column(
                            modifier = Modifier.clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(item.index)
                                }
                            },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "",
                                tint = if (item.index == pagerState.currentPage) item.selectedColor else item.unselectedColor
                            )
                            Text(
                                text = item.title,
                                fontSize = 14.sp,
                                color = if (item.index == pagerState.currentPage) item.selectedColor else item.unselectedColor
                            )
                        }

                    }
                }
            }

        }
    )
}

data class TabItem(
    val index: Int,
    val title: String,
    val icon: ImageVector,
    val selectedColor: Color,
    val unselectedColor: Color
)