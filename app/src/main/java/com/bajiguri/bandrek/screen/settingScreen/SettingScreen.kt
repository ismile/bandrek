package com.bajiguri.bandrek.screen.settingScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anggrayudi.storage.SimpleStorageHelper
import com.bajiguri.bandrek.screen.settingScreen.view.PlatformSheetView
import com.bajiguri.bandrek.utils.PSX_PLATFORM
import com.bajiguri.bandrek.utils.platformMap
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    storageHelper: SimpleStorageHelper
) {
    val scope = rememberCoroutineScope()
    val settings by viewModel.settingList.collectAsState()
    val settingMap = remember(settings) {
        settings.associateBy { it.key }
    }

    var showPlatformSheet by remember { mutableStateOf(false) }
    var selectedPlatform by remember { mutableStateOf(PSX_PLATFORM) }

    Column {
        Text(
            "Platform Settings",
            modifier = Modifier.padding(16.dp)
        )
        HorizontalDivider()
        platformMap.map {
            ListItem(
                headlineContent = { Text(text = it.value.name) },
                modifier = Modifier.clickable {
                    scope.launch {
                        selectedPlatform = it.value
                        showPlatformSheet = true
                    }
                }
            )
        }
    }

    PlatformSheetView(
        show = showPlatformSheet,
        platform = selectedPlatform,
        settingMap = settingMap,
        storageHelper = storageHelper,
        onDismissRequest = { showPlatformSheet = false }
    )
}
