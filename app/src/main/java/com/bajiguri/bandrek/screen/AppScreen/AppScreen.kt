package com.bajiguri.bandrek.screen.AppScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.screen.AppScreen.view.AppIconView
import com.bajiguri.bandrek.screen.AppScreen.view.AppSearchView

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = hiltViewModel(),
    searchText: String
) {
    val appList by viewModel.appList.collectAsState()
    Column(
        modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 80.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(appList.filter {
                it.name.orEmpty().contains(searchText, ignoreCase = true)
            }, key = { it.name.orEmpty() + "_" + it.activityName + "_" + it.packageName }) {
                AppIconView(it = it)
            }
        }
    }
}