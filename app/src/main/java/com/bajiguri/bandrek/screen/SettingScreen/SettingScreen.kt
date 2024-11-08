package com.bajiguri.bandrek.screen.SettingScreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.bajiguri.bandrek.screen.PlatformScreen.PlatformViewModel
import com.bajiguri.bandrek.Setting
import java.io.File

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

    val romFolderLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            viewModel.updateSetting(Setting(ROM_DIRECTORY, uri?.path.orEmpty().replace("/tree/primary:", "")))
            viewModel.updateSetting(Setting(ROM_URI, uri.toString()))

            val childrenUri =
                DocumentsContract.buildChildDocumentsUriUsingTree(
                    uri,
                    DocumentsContract.getTreeDocumentId(uri)
                )
            val tree = DocumentFile.fromTreeUri(context, childrenUri)

            viewModel.scan(tree!!)
        }
    }

    Column {
        ListItem(
            headlineContent = { Text(text = "Directory") },
            supportingContent =  { Text(text = settingMap[ROM_DIRECTORY]?.value.orEmpty()) },
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                romFolderLauncher.launch(intent)
            }
        )
    }
}
