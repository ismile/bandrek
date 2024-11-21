package com.bajiguri.bandrek.screen.appScreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.screen.appScreen.AppInfo
import com.bajiguri.bandrek.screen.appScreen.AppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSheetView(
    show: Boolean,
    app: AppInfo,
    onDismissRequest: () -> Unit,
    viewModel: AppViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    val scope = rememberCoroutineScope()

    if (show) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Column {
                ListItem(
                    headlineContent = { Text(text = "Add to Game Library") },
                    modifier = Modifier.clickable {
                        scope.launch {
                            viewModel.addToGameLibrary(app)
                        }
                    }
                )
            }

        }
    }
}