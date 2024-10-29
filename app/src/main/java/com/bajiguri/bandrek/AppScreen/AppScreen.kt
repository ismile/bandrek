package com.bajiguri.bandrek.AppScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.AppScreen.view.AppIconView

@Composable
fun AppScreen(modifier: Modifier = Modifier, viewModel: AppViewModel = hiltViewModel()) {
    val appList by viewModel.appList.collectAsState()

    Column(modifier) {
        Text(text = "Installed Apps")
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 80.dp)) {
            items(appList, key = { it.name.orEmpty() }) {
                AppIconView(it = it)
            }
        }
    }

}