package com.bajiguri.bandrek.screen.RomScreen

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import com.anggrayudi.storage.file.DocumentFileCompat
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.utils.playerMap

@Composable
fun RomScreen(platformCode: String, viewModel: RomViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val romList by viewModel.getRomList(platformCode).collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
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
            .clickable {
                if(playerMap.containsKey(rom.platformCode)) {
                    playerMap[rom.platformCode]?.values?.first()?.let { it(rom, context) }
                }
            }
    ) {
        AsyncImage(
            model = DocumentFileCompat.fromUri(context, Uri.parse(rom.locationUri+".jpg"))!!.uri,
            contentDescription = "",
            modifier = Modifier
                .width(100.dp)
                .aspectRatio(335f / 300f)
                .clip(RoundedCornerShape(6.dp)),
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