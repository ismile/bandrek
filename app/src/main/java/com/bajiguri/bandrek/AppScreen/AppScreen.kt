package com.bajiguri.bandrek.AppScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppScreen(modifier: Modifier = Modifier, viewModel: AppViewModel = hiltViewModel()) {
    val appList by viewModel.appList.collectAsState()

    Column {
        Text(text = "Installed Apps")
        LazyColumn(modifier) {
            items(appList, key = { it.packageName }) { app ->
                Text(text = app.name)
            }
        }
    }

}