package com.bajiguri.bandrek.screen.PlatformScreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.R
import com.bajiguri.bandrek.screen.AppScreen.view.iconWidth
import com.bajiguri.bandrek.utils.GBA_PLATFORM
import com.bajiguri.bandrek.utils.N3DS_PLATFORM
import com.bajiguri.bandrek.utils.NDS_PLATFORM
import com.bajiguri.bandrek.utils.PS2_PLATFORM
import com.bajiguri.bandrek.utils.PSP_PLATFORM
import com.bajiguri.bandrek.utils.PSX_PLATFORM
import kotlin.math.absoluteValue

@Composable
fun PlatformItemView(pagerState: PagerState, page: Int, platform: Platform, onItemClick: (platformCode: String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            Modifier
                .clickable {
                    onItemClick(platform.code)
                }
                .fillMaxSize()
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            PlatformItemImageView(platform)
        }
    }
}

@Composable
fun PlatformItemImageView(platform: Platform) {
    val painter = remember(platform.code) {
        when (platform.code) {
            "psx" -> {
                R.drawable.portrait_psx
            }

            "gba" -> {
                R.drawable.portrait_gba
            }

            "nds" -> {
                R.drawable.portrait_ds
            }

            "3ds" -> {
                R.drawable.portrait_3ds
            }

            "psp" -> {
                R.drawable.portrait_psp
            }

            "ps2" -> {
                R.drawable.portrait_ps2
            }

            else -> R.drawable.ic_launcher_background
        }
    }
    AsyncImage(
        model = painter,
        contentDescription = platform.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )
}