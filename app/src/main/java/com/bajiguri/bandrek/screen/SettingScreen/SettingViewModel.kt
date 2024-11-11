package com.bajiguri.bandrek.screen.SettingScreen

import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Setting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    fun scan(root: DocumentFile, platform: Platform) {
        viewModelScope.launch {
            settingRepository.upsertPlatform(platform)
            val roms = root.listFiles()
                .filter {
                    it.isFile && platform.fileExtension.split(",").contains(
                        it.name?.substring(it.name.orEmpty().lastIndexOf(".").plus(1))
                            ?.lowercase()
                    )
                }
            settingRepository.saveRom(
                roms, platform.code
            )

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