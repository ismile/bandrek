package com.bajiguri.bandrek.screen.RomScreen

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.bajiguri.bandrek.screen.RomScreen.View.RomScannerSheetView
import com.bajiguri.bandrek.screen.RomScreen.View.RomSheetView
import com.bajiguri.bandrek.utils.PSX_PLATFORM
import com.bajiguri.bandrek.utils.playerMap
import kotlinx.coroutines.launch

@Composable
fun RomScreen(platformCode: String, viewModel: RomViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val romList by viewModel.getRomList(platformCode).collectAsState()
    var showRomSheet by remember { mutableStateOf(false) }
    var showRomScannerSheet by remember { mutableStateOf(false) }
    var selectedRom by remember { mutableStateOf(Rom()) }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(romList) {
            RomItem(it, onLongClick = {
                selectedRom = it
                showRomSheet = true
            })
        }
    }
    RomSheetView(showRomSheet, selectedRom, onDismissRequest = { showRomSheet = false }, onScanClick = {
        showRomSheet = false
        showRomScannerSheet = true
    })
    RomScannerSheetView(showRomScannerSheet, selectedRom, onDismissRequest = { showRomScannerSheet = false })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RomItem(rom: Rom, onLongClick: () -> Unit) {
    var context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .combinedClickable(
                onClick = {
                    if (playerMap.containsKey(rom.platformCode)) {
                        playerMap[rom.platformCode]?.values
                            ?.first()
                            ?.let { it(rom, context) }
                    }
                },
                onLongClick = {
                    scope.launch {
                        onLongClick()
                    }
                }
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(rom.coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
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