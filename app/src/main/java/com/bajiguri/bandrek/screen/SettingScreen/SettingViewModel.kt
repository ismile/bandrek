package com.bajiguri.bandrek.screen.SettingScreen

import android.R.attr.path
import android.os.Environment
import androidx.compose.ui.text.toLowerCase
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.Setting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    val settingList: StateFlow<List<Setting>> = settingRepository.getAllSetting()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Optional: Stop collecting after 5 seconds of inactivity
            initialValue = emptyList()
        )

    fun updateSetting(setting: Setting) {
        viewModelScope.launch {
            settingRepository.upsertSetting(setting)
        }
    }

    fun scan(root: DocumentFile) {
        viewModelScope.launch {
            val platformDir = root.listFiles()
                .filter { it.isDirectory }
            val platforms = settingRepository.savePlatform(platformDir)

            platforms.forEach { x ->
                val roms = x.first.listFiles()
                    .filter {
                        it.isFile && x.second.fileExtension.split(",").contains(
                            it.name?.substring(it.name.orEmpty().lastIndexOf(".").plus(1))
                                ?.lowercase()
                        )
                    }
                settingRepository.saveRom(roms, x.second.code
                )
            }
        }

    }

//    private val _settingList = MutableStateFlow<List<Setting>>(emptyList())
//    val settingList: StateFlow<List<Setting>> = _settingList.asStateFlow()

//    fun refreshSettings() {
//        viewModelScope.launch {
//            settingRepository.getAllSetting().collect { settings ->
//                _settingList.value = settings
//            }
//        }
//    }

//    init {
//        viewModelScope.launch {
//            _settingList.value = settingRepository.getAllSetting().first()
//        }
//    }
}