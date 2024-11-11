package com.bajiguri.bandrek.screen.SettingScreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.anggrayudi.storage.SimpleStorageHelper
import com.anggrayudi.storage.file.DocumentFileCompat
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Setting
import com.bajiguri.bandrek.utils.GBA_PLATFORM
import com.bajiguri.bandrek.utils.N3DS_PLATFORM
import com.bajiguri.bandrek.utils.NDS_PLATFORM
import com.bajiguri.bandrek.utils.PS2_PLATFORM
import com.bajiguri.bandrek.utils.PSP_PLATFORM
import com.bajiguri.bandrek.utils.PSX_PLATFORM
import com.bajiguri.bandrek.utils.SWITCH_PLATFORM

@Composable
@Preview(showBackground = true)
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val settings by viewModel.settingList.collectAsState();
    val settingMap = remember(settings) {
        settings.associateBy { it.key }
    }

    val psxFolderLauncher = createFolderLauncher(context, viewModel, "psx", PSX_PLATFORM)
    val ps2FolderLauncher = createFolderLauncher(context, viewModel, "ps2", PS2_PLATFORM)
    val pspFolderLauncher = createFolderLauncher(context, viewModel, "psp", PSP_PLATFORM)
    val gbaFolderLauncher = createFolderLauncher(context, viewModel, "gba", GBA_PLATFORM)
    val ndsFolderLauncher = createFolderLauncher(context, viewModel, "nds", NDS_PLATFORM)
    val n3dsFolderLauncher = createFolderLauncher(context, viewModel, "3ds", N3DS_PLATFORM)
    val switchFolderLauncher = createFolderLauncher(context, viewModel, "switch", SWITCH_PLATFORM)

    Column {
        ListItem(
            headlineContent = { Text(text = "Play Station") },
            supportingContent = { Text(text = settingMap["psx_dir"]?.value.orEmpty()) },
            modifier = Modifier.clickable {
                DocumentFileCompat.getAccessibleAbsolutePaths(context);
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                psxFolderLauncher.launch(intent)
            }
        )
        ListItem(
            headlineContent = { Text(text = "Play Station 2") },
            supportingContent = { Text(text = settingMap["ps2_dir"]?.value.orEmpty()) },
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                ps2FolderLauncher.launch(intent)
            }
        )
        ListItem(
            headlineContent = { Text(text = "Play Station Portable") },
            supportingContent = { Text(text = settingMap["psp_dir"]?.value.orEmpty()) },
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                pspFolderLauncher.launch(intent)
            }
        )
        ListItem(
            headlineContent = { Text(text = "Game Boy Advanced") },
            supportingContent = { Text(text = settingMap["gba_dir"]?.value.orEmpty()) },
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                gbaFolderLauncher.launch(intent)
            }
        )
        ListItem(
            headlineContent = { Text(text = "Nintendo DS") },
            supportingContent = { Text(text = settingMap["nds_dir"]?.value.orEmpty()) },
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                ndsFolderLauncher.launch(intent)
            }
        )
        ListItem(
            headlineContent = { Text(text = "Nintendo 3DS") },
            supportingContent = { Text(text = settingMap["3ds_dir"]?.value.orEmpty()) },
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                n3dsFolderLauncher.launch(intent)
            }
        )
        ListItem(
            headlineContent = { Text(text = "Nintendo Switch") },
            supportingContent = { Text(text = settingMap["switch_dir"]?.value.orEmpty()) },
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                switchFolderLauncher.launch(intent)
            }
        )

    }
}


@Composable
fun createFolderLauncher(
    context: Context,
    viewModel: SettingViewModel,
    settingPrefix: String,
    platform: Platform
): ActivityResultLauncher<Intent> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            viewModel.updateSetting(Setting("${settingPrefix}_dir", uri?.path.orEmpty().replace("/tree/primary:", "")))
            viewModel.updateSetting(Setting("${settingPrefix}_uri", uri.toString()))

            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri))
            val tree = DocumentFile.fromTreeUri(context, childrenUri)

            viewModel.scan(tree!!, platform)
        }
    }
}