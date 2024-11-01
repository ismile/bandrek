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
import com.bajiguri.bandrek.MainScreen.view.NavigationView

@Composable
fun Navigation() {
    val navController = rememberNavController()
    var searchText by remember { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "app_screen") {
        composable("app_screen") {
            Scaffold(bottomBar = {
                NavigationView(
                    searchText,
                    onSearchTextChange = { searchText = it })
            }) { padding ->
                AppScreen(modifier = Modifier.padding(padding), searchText = searchText)
            }
        }
    }
}