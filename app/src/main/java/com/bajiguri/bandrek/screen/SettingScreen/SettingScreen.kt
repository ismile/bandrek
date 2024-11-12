package com.bajiguri.bandrek.screen.SettingScreen

import android.provider.DocumentsContract
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.anggrayudi.storage.SimpleStorageHelper
import com.anggrayudi.storage.file.DocumentFileCompat
import com.anggrayudi.storage.file.getAbsolutePath
import com.bajiguri.bandrek.Setting
import com.bajiguri.bandrek.utils.platformMap

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    storageHelper: SimpleStorageHelper
) {
    val context = LocalContext.current
    val settings by viewModel.settingList.collectAsState();
    val settingMap = remember(settings) {
        settings.associateBy { it.key }
    }

    LaunchedEffect(true) {
        storageHelper.onStorageAccessGranted = { requestCode, root ->
            viewModel.updateSetting(Setting(root.name+"_dir", root.getAbsolutePath(context)))
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                root.uri,
                DocumentsContract.getTreeDocumentId(root.uri)
            )
            val tree = DocumentFile.fromTreeUri(context, childrenUri)
            if(platformMap.containsKey(root.name.orEmpty().lowercase())) {
                viewModel.scan(tree!!, platformMap[root.name]!!)
            }
        }
    }

    Column {
        ListItem(
            headlineContent = { Text(text = "cek") },
            modifier = Modifier.clickable {
                DocumentFileCompat.getAccessibleAbsolutePaths(context)
            }
        )
        platformMap.map {
            ListItem(
                headlineContent = { Text(text = it.value.name) },
                supportingContent = { Text(text = settingMap[it.value.code+"_dir"]?.value.orEmpty()) },
                modifier = Modifier.clickable {
                    storageHelper.requestStorageAccess()
                }
            )
        }
    }
}
