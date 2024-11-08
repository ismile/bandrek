package com.bajiguri.bandrek.screen.AppScreen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.bajiguri.bandrek.screen.AppScreen.AppInfo
import com.bajiguri.bandrek.screen.AppScreen.startApp

val iconWidth = 60.dp
val iconContainerWidth = iconWidth + 10.dp
val iconContainerHeight = iconWidth + 40.dp

@Composable
fun AppIconView(
    modifier: Modifier = Modifier,
    it: AppInfo,
    showText: Boolean = true
) {
    var context = LocalContext.current

    Column(
        modifier = modifier
            .size(iconContainerWidth, iconContainerHeight)
            .padding(4.dp)
            .clickable(){
                startApp(context, it)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(iconWidth, iconWidth)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = it.drawable,
                contentDescription = it.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(iconWidth, iconWidth)
            )
        }
        Text(
            text = it.name ?: "",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 13.sp,
            modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 0.dp)
        )
    }
}