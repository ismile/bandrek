package com.bajiguri.bandrek.MainScreen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bajiguri.bandrek.AppScreen.view.AppSearchView
import com.bajiguri.bandrek.icon.Component
import com.bajiguri.bandrek.icon.Gamepad2
import com.bajiguri.bandrek.icon.House
import com.bajiguri.bandrek.icon.Settings

data class TopLevelRoute(val name: String, val route: String, val icon: ImageVector)
val topLevelRoutes = listOf(
    TopLevelRoute("Home", "platform_screen", House),
    TopLevelRoute("Platform", "platform_screen", Gamepad2),
    TopLevelRoute("App", "app_screen", Component),
    TopLevelRoute("Setting", "platform_screen", Settings)
)

@Composable
fun NavigationView(searchedText: String = "",
                   onSearchTextChange: (String) -> Unit = {},
                   navHostController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(
        modifier =
        Modifier
//            .padding(10.dp)
            .background(Color.Black)
    ) {
        AppSearchView(value = searchedText, onValueChange = onSearchTextChange)
        NavigationBar(
            containerColor = Color.Black,
            windowInsets = WindowInsets(0,0,0,40),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            topLevelRoutes.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.name,
                        )
                    },
                    selected = currentDestination?.route.orEmpty() == item.route,
                    onClick = {
                        selectedItem = index
                        navHostController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

}