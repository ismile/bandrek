package com.bajiguri.bandrek.screen.romScreen.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.screen.appScreen.AppInfo
import com.bajiguri.bandrek.screen.appScreen.startApp
import com.bajiguri.bandrek.screen.romScreen.RomViewModel
import com.bajiguri.bandrek.utils.ANDROID_PLATFORM
import com.bajiguri.bandrek.utils.playerMap
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RomItemView(rom: Rom, onLongClick: () -> Unit, viewModel: RomViewModel = hiltViewModel()) {
    val selectedRom by viewModel.selectedRom.collectAsState()
    var context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bgColor by animateColorAsState(
        if (selectedRom.code == rom.code) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface,
        label = "border"
    )


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .background(bgColor)
            .combinedClickable(onClick = {
                scope.launch {
                    if (selectedRom.code == rom.code) {
                        if (rom.platformCode == ANDROID_PLATFORM.code) {
                            startApp(
                                context, AppInfo(
                                    name = rom.name,
                                    packageName = rom.packageName.orEmpty(),
                                    activityName = rom.activityName.orEmpty()
                                )
                            )
                        } else {
                            if (playerMap.containsKey(rom.platformCode)) {
                                playerMap[rom.platformCode]?.values
                                    ?.first()
                                    ?.let { x -> x(rom, context) }
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .clip(MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(rom.coverUrl).crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier
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

}