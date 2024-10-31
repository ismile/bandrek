package com.bajiguri.bandrek

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bajiguri.bandrek.AppScreen.AppScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "app_screen") {
        composable("app_screen") {
            AppScreen()
        }
    }
}