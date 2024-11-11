package com.bajiguri.bandrek.screen.RomScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.Uri
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.bajiguri.bandrek.R
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.components.icon.Gamepad2
import com.bajiguri.bandrek.utils.launchDuckStation

@Composable
fun RomScreen(platformCode: String, viewModel: RomViewModel = hiltViewModel()) {
    val romList by viewModel.getRomList(platformCode).collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(romList) {
            RomItem(it)
        }
    }
}

@Composable
fun RomItem(rom: Rom) {
    var context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .width(120.dp)
            .height(160.dp)
            .clickable {
                launchDuckStation(rom, context)
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri(rom.locationUri+".jpg"))
                .build(),
            contentDescription = "",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .background(MaterialTheme.colorScheme.surfaceDim, shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit,
        )
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .width(100.dp)
//                .height(100.dp)
//                .background(MaterialTheme.colorScheme.surfaceDim, shape = RoundedCornerShape(16.dp))
//        ) {
//            Icon(Gamepad2, contentDescription = rom.name, modifier = Modifier.size(50.dp))
//        }

        Text(
            text = rom.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 13.sp,
            modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 0.dp)
        )
    }

}