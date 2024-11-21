package com.bajiguri.bandrek.screen.platformScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.screen.platformScreen.view.PlatformItemView

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