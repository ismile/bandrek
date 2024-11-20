package com.bajiguri.bandrek.screen.RomScreen

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.anggrayudi.storage.file.DocumentFileCompat
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.screen.AppScreen.AppInfo
import com.bajiguri.bandrek.screen.AppScreen.startApp
import com.bajiguri.bandrek.screen.RomScreen.View.RomScannerSheetView
import com.bajiguri.bandrek.screen.RomScreen.View.RomSheetView
import com.bajiguri.bandrek.utils.ANDROID_PLATFORM
import com.bajiguri.bandrek.utils.PSX_PLATFORM
import com.bajiguri.bandrek.utils.playerMap
import kotlinx.coroutines.launch
import kotlin.code

@Composable
fun RomScreen(platformCode: String, viewModel: RomViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val romList by viewModel.getRomList(platformCode).collectAsState()
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(minSize = 130.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(romList, key = { rom -> rom.code }) {
            RomItem(it,
                onLongClick = {
                    viewModel.setSelectedRom(it)
                    viewModel.toggleRomSheet(true)
                })
        }
    }
    RomSheetView()
    RomScannerSheetView()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RomItem(rom: Rom, onLongClick: () -> Unit, viewModel: RomViewModel = hiltViewModel()) {
    val selectedRom by viewModel.selectedRom.collectAsState()
    var context = LocalContext.current
    val scope = rememberCoroutineScope()
    val borderWidth by animateDpAsState(if (selectedRom.code == rom.code) 4.dp else 0.dp, label = "border")


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
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
                .height(200.dp)
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