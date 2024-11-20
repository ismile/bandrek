package com.bajiguri.bandrek.screen.RomScreen.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.external.iddb.Game
import com.bajiguri.bandrek.screen.RomScreen.RomViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomScannerSheetView(
    viewModel: RomViewModel = hiltViewModel()
) {
    val show by viewModel.showRomScannerSheet.collectAsState()
    val rom by viewModel.selectedRom.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    var percentage by remember { mutableFloatStateOf(0f) }
    var romName by remember { mutableStateOf(rom.name) }
    val gameList = remember { mutableStateListOf<Game>() }

    val context = LocalContext.current

    LaunchedEffect(rom.code) {
        romName = rom.name
    }

    if (show) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = {
                viewModel.toggleScannerSheet(false)
            }
        ) {
            Column {
                OutlinedTextField(
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                scope.launch {
                                    val games = viewModel.searchByName(romName, rom.platformCode)
                                    gameList.clear()
                                    gameList.addAll(games)
                                }
                            }
                        )
                    },
                    value = romName, onValueChange = { romName = it },
                    modifier = Modifier.fillMaxWidth()
                )
                if (loading) {
                    LinearProgressIndicator(
                        progress = { percentage },
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    HorizontalDivider()
                }

                gameList.map {
                    ListItem(
                        headlineContent = { Text(text = it.name) },
//                        supportingContent = { Text(text = it.summary.orEmpty()) },
                        modifier = Modifier.clickable {
                            scope.launch {
                                loading = true
                                viewModel.upsertRom(rom.copy(
                                    name = it.name,
                                    description = it.summary.orEmpty(),
                                    genres = it.genres.joinToString(","),
                                    rating = it.rating,
                                    coverUrl = "https:"+it.cover.url.replace("t_thumb", "t_cover_big"),
                                    artworkUrl = "https:"+it.artworks?.first()?.url?.replace("t_thumb", "t_1080p"),
                                ))
                                loading = false
                            }

                        }
                    )
                }
            }

        }
    }
}