package com.bajiguri.bandrek.screen.romScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.screen.appScreen.AppInfo
import com.bajiguri.bandrek.screen.appScreen.startApp
import com.bajiguri.bandrek.screen.romScreen.view.RomScannerSheetView
import com.bajiguri.bandrek.screen.romScreen.view.RomSheetView
import com.bajiguri.bandrek.utils.ANDROID_PLATFORM
import com.bajiguri.bandrek.utils.GBA_PLATFORM
import com.bajiguri.bandrek.utils.playerMap
import kotlinx.coroutines.launch

@Composable
fun PlatformAndRomScreen(modifier: Modifier = Modifier, viewModel: RomViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val selectedPlatform by viewModel.selectedPlatform.collectAsState()
    val selectedRom by viewModel.selectedRom.collectAsState()
    val romList by viewModel.getRomList(GBA_PLATFORM.code).collectAsState()
    val gridState = rememberLazyGridState()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = {
        romList.size
    })

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(selectedRom.coverUrl)
            .crossfade(true)
            .build(),
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.3f),
        contentScale = ContentScale.Crop,
    )
    Column {
        Column(
            modifier = Modifier.weight(1f),
        ) {  }
        HorizontalPager(state = pagerState, pageSize = PageSize.Fixed(120.dp)) { page ->
            RomItemDetail(romList[page],
                onLongClick = {
                    viewModel.setSelectedRom(romList[page])
                    viewModel.toggleRomSheet(true)
                })
        }
    }
    RomSheetView()
    RomScannerSheetView()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RomItemDetail(rom: Rom, onLongClick: () -> Unit, viewModel: RomViewModel = hiltViewModel()) {
    val selectedRom by viewModel.selectedRom.collectAsState()
    var context = LocalContext.current
    val scope = rememberCoroutineScope()
    val borderWidth by animateDpAsState(if (selectedRom.code == rom.code) 4.dp else 0.dp, label = "border")


    Column(
        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .combinedClickable(onClick = {
                scope.launch {
                    if (selectedRom.code == rom.code) {
                        if(rom.platformCode == ANDROID_PLATFORM.code) {
                            startApp(context, AppInfo(
                                name = rom.name,
                                packageName = rom.packageName.orEmpty(),
                                activityName = rom.activityName.orEmpty()
                            ))
                        } else {
                            if (playerMap.containsKey(rom.platformCode)) {
                                playerMap[rom.platformCode]?.values?.first()?.let { x -> x(rom, context) }
                            }
                        }
                    }
                    viewModel.setSelectedRom(rom)
                }
            }, onLongClick = {
                scope.launch {
                    onLongClick()
                }
            })
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(rom.coverUrl).
                crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .border(
                    borderWidth,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.shapes.medium
                )
                .fillMaxWidth()
                .height(120.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = rom.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 13.sp,
            modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 0.dp)
        )
    }

}