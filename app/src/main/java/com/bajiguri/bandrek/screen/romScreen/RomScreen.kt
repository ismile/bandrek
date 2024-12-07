package com.bajiguri.bandrek.screen.romScreen

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.SuggestionChip
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bajiguri.bandrek.screen.platformScreen.view.PlatformItemView
import com.bajiguri.bandrek.screen.romScreen.view.RomItemView
import com.bajiguri.bandrek.screen.romScreen.view.RomListLandscapeView
import com.bajiguri.bandrek.screen.romScreen.view.RomListView
import com.bajiguri.bandrek.screen.romScreen.view.RomScannerSheetView
import com.bajiguri.bandrek.screen.romScreen.view.RomSheetView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomScreen(platformCode: String, viewModel: RomViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

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
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                BackgroundRom()
                HorizontalPager(state = pagerState) { page ->
                    RomListLandscapeView(platformCode = platforms[pagerState.currentPage].code)
                }
            } else {
                HorizontalPager(state = pagerState) { page ->
                    RomListView(platformCode = platforms[pagerState.currentPage].code)
                }
            }
        }


    }

    RomSheetView()
    RomScannerSheetView()
}

@Composable
fun BackgroundRom(viewModel: RomViewModel = hiltViewModel()) {
    val selectedRom by viewModel.selectedRom.collectAsState()
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(selectedRom.coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .blur(radiusX = 5.dp, radiusY = 5.dp)
//                .alpha(0.4f)
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    val colors = listOf(
                        Color.Black,
                        Color.Transparent
                    )
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.DstIn
                    )
                },
            contentScale = ContentScale.FillWidth,
        )
        Box { }
        Row(modifier = Modifier.padding(18.dp, 0.dp)) {
            Column {
                Text(
                    text = selectedRom.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Row {
                    if(selectedRom.genres.isNotEmpty())
                    selectedRom.genres.split(",").map {
                        SuggestionChip(
                            modifier = Modifier.padding(end = 10.dp),
                            onClick = { },
                            label = { Text(it) }
                        )
                    }
                }
            }

        }
    }

}