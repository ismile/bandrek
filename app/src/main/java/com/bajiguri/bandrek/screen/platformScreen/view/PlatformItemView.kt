package com.bajiguri.bandrek.screen.platformScreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.R
import com.bajiguri.bandrek.utils.ANDROID_CODE
import com.bajiguri.bandrek.utils.GBA_CODE
import com.bajiguri.bandrek.utils.N3DS_CODE
import com.bajiguri.bandrek.utils.NDS_CODE
import com.bajiguri.bandrek.utils.PS2_CODE
import com.bajiguri.bandrek.utils.PSP_CODE
import com.bajiguri.bandrek.utils.PSX_CODE
import com.bajiguri.bandrek.utils.SWITCH_CODE
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
            PSX_CODE -> {
                R.drawable.portrait_psx
            }

            GBA_CODE -> {
                R.drawable.portrait_gba
            }

            NDS_CODE -> {
                R.drawable.portrait_ds
            }

            N3DS_CODE -> {
                R.drawable.portrait_3ds
            }

            PSP_CODE -> {
                R.drawable.portrait_psp
            }

            PS2_CODE -> {
                R.drawable.portrait_ps2
            }

            SWITCH_CODE -> {
                R.drawable.portrait_switch
            }

            ANDROID_CODE -> {
                R.drawable.portrait_android
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