package com.bajiguri.bandrek.screen.romScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.screen.platformScreen.view.PlatformItemView
import com.bajiguri.bandrek.screen.romScreen.view.RomItemView
import com.bajiguri.bandrek.screen.romScreen.view.RomListView
import com.bajiguri.bandrek.screen.romScreen.view.RomScannerSheetView
import com.bajiguri.bandrek.screen.romScreen.view.RomSheetView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomScreen(platformCode: String, viewModel: RomViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val platforms by viewModel.platformList.collectAsState();
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        if (platforms.isNotEmpty()) {
            val pagerState = rememberPagerState(initialPage = 0, pageCount = {
                platforms.size
            })
            PrimaryScrollableTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = pagerState.currentPage,
                tabs = {
                    platforms.mapIndexed { index, it ->
                        Tab(selected = pagerState.currentPage == index, onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }

                        }, text = { Text(it.code.uppercase()) })
                    }
                }
            )
            HorizontalPager(state = pagerState) { page ->
                RomListView(platformCode = platforms[pagerState.currentPage].code)
            }
        }


    }

    RomSheetView()
    RomScannerSheetView()
}