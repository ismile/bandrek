package com.bajiguri.bandrek.screen.PlatformScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.screen.PlatformScreen.view.PlatformItemView
import com.bajiguri.bandrek.screen.SettingScreen.SettingViewModel
import kotlin.math.absoluteValue

@Composable
fun PlatformScreen(viewModel: PlatformViewModel = hiltViewModel(), onItemClick: (platformCode: String) -> Unit) {
    val settings by viewModel.platformList.collectAsState();

    if (settings.isNotEmpty()) {
        val pagerState = rememberPagerState(initialPage = 0, pageCount = {
            settings.size
        })
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(state = pagerState) { page ->
                PlatformItemView(pagerState, page, settings[page], onItemClick)
            }
        }
    }

}