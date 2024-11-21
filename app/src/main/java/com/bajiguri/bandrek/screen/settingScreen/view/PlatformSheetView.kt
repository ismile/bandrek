package com.bajiguri.bandrek.screen.settingScreen.view

import android.provider.DocumentsContract
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
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.anggrayudi.storage.SimpleStorageHelper
import com.anggrayudi.storage.file.getAbsolutePath
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Setting
import com.bajiguri.bandrek.screen.settingScreen.SettingViewModel
import com.bajiguri.bandrek.utils.platformMap
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformSheetView(
    show: Boolean,
    platform: Platform,
    settingMap: Map<String, Setting>,
    storageHelper: SimpleStorageHelper,
    onDismissRequest: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    var percentage by remember { mutableFloatStateOf(0f) }

    val context = LocalContext.current

    LaunchedEffect(true) {
        storageHelper.onStorageAccessGranted = { requestCode, root ->
            viewModel.updateSetting(Setting(root.name + "_dir", root.getAbsolutePath(context)))
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                root.uri,
                DocumentsContract.getTreeDocumentId(root.uri)
            )
            val tree = DocumentFile.fromTreeUri(context, childrenUri)
            if (platformMap.containsKey(root.name.orEmpty().lowercase())) {
                viewModel.scan(tree!!, platformMap[root.name]!!)
            }
        }
    }

    if (show) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Column {
                Text(
                    platform.name,
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
                    headlineContent = { Text(text = "Path") },
                    supportingContent = { Text(text = settingMap[platform.code + "_dir"]?.value.orEmpty()) },
                    modifier = Modifier.clickable {
                        scope.launch {
                            storageHelper.requestStorageAccess()
                        }
                    }
                )
                ListItem(
                    headlineContent = { Text(text = "Scan") },
                    modifier = Modifier.clickable {
                        scope.launch {
                            percentage = 0f
                            loading = true
                            viewModel.scan(platform, { percentage = it })
                            percentage = 0f
                            loading = false
                        }
                    }
                )
            }

        }
    }
}