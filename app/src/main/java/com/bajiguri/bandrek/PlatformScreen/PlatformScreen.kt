package com.bajiguri.bandrek.PlatformScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PlatformScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "PLATFORM",
            modifier = Modifier
                .align(alignment = Alignment.Center)
        )
    }
}