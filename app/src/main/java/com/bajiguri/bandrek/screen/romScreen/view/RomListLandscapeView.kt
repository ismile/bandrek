package com.bajiguri.bandrek.screen.romScreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.screen.romScreen.RomViewModel

@Composable
fun RomListLandscapeView(viewModel: RomViewModel = hiltViewModel(), platformCode: String = "") {
    val romList by viewModel.getRomList(platformCode).collectAsState()
    val gridState = rememberLazyGridState()

    LazyHorizontalGrid(
        state = gridState,
        rows = GridCells.Fixed(1),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(romList, key = { rom -> rom.code }) {
            RomItemView(
                modifier = Modifier.width(120.dp),
                rom = it,
                onLongClick = {
                    viewModel.setSelectedRom(it)
                    viewModel.toggleRomSheet(true)
                })
        }
    }
}