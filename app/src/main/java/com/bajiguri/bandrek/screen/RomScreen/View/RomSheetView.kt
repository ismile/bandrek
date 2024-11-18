package com.bajiguri.bandrek.screen.RomScreen.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.screen.RomScreen.RomViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomSheetView(
    show: Boolean,
    rom: Rom,
    onDismissRequest: () -> Unit,
    onScanClick: () -> Unit,
    viewModel: RomViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    var percentage by remember { mutableFloatStateOf(0f) }
    var romName by remember { mutableStateOf(rom.name) }

    val context = LocalContext.current

    LaunchedEffect(rom.code) {
        romName = rom.name
    }

    if (show) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Column {
                Text(
                    rom.name,
                    modifier = Modifier.padding(16.dp)
                )
                if (loading) {
                    LinearProgressIndicator(
                        progress = { percentage },
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    HorizontalDivider()
                }

                ListItem(
                    headlineContent = { Text(text = "ID") },
                    supportingContent = { Text(text = rom.code ) }
                )
                ListItem(
                    headlineContent = { Text(text = "Re-Scan") },
                    modifier = Modifier.clickable {
                        scope.launch {
                            onScanClick()
                        }
                    }
                )
            }

        }
    }
}