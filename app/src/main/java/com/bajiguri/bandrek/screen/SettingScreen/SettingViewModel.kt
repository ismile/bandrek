package com.bajiguri.bandrek.screen.SettingScreen

import android.R.attr.path
import android.os.Environment
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

    fun scan() {
        viewModelScope.launch {
            val path = settingList.value.find { x -> x.key == ROM_DIRECTORY }
            val directories = settingRepository.getDirectories(path?.value.orEmpty())
            settingRepository.savePlatform(directories)
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