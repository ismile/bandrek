package com.bajiguri.bandrek.screen.RomScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.screen.PlatformScreen.PlatformViewModel

@Composable
fun RomScreen(platformCode: String, viewModel: RomViewModel = hiltViewModel()) {
    val romList by viewModel.getRomList(platformCode).collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        romList.map {
            Text(
                text = it.name,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
            )
        }
    }

}