package com.bajiguri.bandrek

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bajiguri.bandrek.AppScreen.AppScreen
import com.bajiguri.bandrek.HomeScreen.HomeScreen
import com.bajiguri.bandrek.HomeScreen.view.NavigationView
import com.bajiguri.bandrek.PlatformScreen.PlatformScreen
import com.bajiguri.bandrek.SettingScreen.SettingScreen

@Composable
fun Navigation() {
    var searchText by remember { mutableStateOf("") }
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        NavigationView(
            searchText,
            onSearchTextChange = { searchText = it },
            navController)
    }) { padding ->
        NavHost(navController, startDestination = "app_screen", Modifier.padding(padding)) {
            composable("home_screen") {
                HomeScreen()
            }
            composable("app_screen") {
                AppScreen(modifier = Modifier.padding(padding), searchText = searchText)
            }
            composable("platform_screen") {
                PlatformScreen()
            }
            composable("setting_screen") {
                SettingScreen()
            }
        }

    }


}