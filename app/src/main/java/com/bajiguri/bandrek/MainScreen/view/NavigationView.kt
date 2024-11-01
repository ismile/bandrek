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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.Navigation
import com.bajiguri.bandrek.AppScreen.view.AppSearchView
import com.bajiguri.bandrek.icon.Component
import com.bajiguri.bandrek.icon.Gamepad2
import com.bajiguri.bandrek.icon.House
import com.bajiguri.bandrek.icon.Settings

val items = listOf("Home", "Platform", "App", "Setting")
val selectedIcons = listOf(House, Gamepad2, Component, Settings)

@Composable
@Preview(showBackground = true)
fun NavigationView(searchedText: String = "", onSearchTextChange: (String) -> Unit = {}) {
    var selectedItem by remember { mutableIntStateOf(0) }
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
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index }
                )
            }
        }
    }

}