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
import com.anggrayudi.storage.SimpleStorageHelper
import com.bajiguri.bandrek.screen.appScreen.AppScreen
import com.bajiguri.bandrek.screen.homeScreen.HomeScreen
import com.bajiguri.bandrek.screen.homeScreen.view.NavigationView
import com.bajiguri.bandrek.screen.platformScreen.PlatformScreen
import com.bajiguri.bandrek.screen.romScreen.PlatformAndRomScreen
import com.bajiguri.bandrek.screen.romScreen.RomScreen
import com.bajiguri.bandrek.screen.settingScreen.SettingScreen

@Composable
fun Navigation(
    storageHelper: SimpleStorageHelper
) {
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
                PlatformAndRomScreen(modifier = Modifier.padding(padding))
//                PlatformScreen(onItemClick = {
//                    navController.navigate("rom_screen/$it")
//                })
            }
            composable("setting_screen") {
                SettingScreen(storageHelper= storageHelper)
            }
            composable("rom_screen/{platformCode}") { navBackStackEntry ->
                val platformCode = navBackStackEntry.arguments?.getString("platformCode")
                platformCode?.let {
                    RomScreen(platformCode = it)
                }
            }
        }

    }


}