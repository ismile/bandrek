package com.bajiguri.bandrek.SettingScreen

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.AppDao
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

//    private val _settingList = MutableStateFlow<List<Setting>>(emptyList())
//    val settingList: StateFlow<List<Setting>> = _settingList.asStateFlow()

//    fun refreshSettings() {
//        viewModelScope.launch {
//            settingRepository.getAllSetting().collect { settings ->
//                _settingList.value = settings
//            }
//        }
//    }

    fun updateSetting(setting: Setting) {
        viewModelScope.launch {
            settingRepository.upsertSetting(setting)
        }
//        refreshSettings()
    }

//    init {
//        viewModelScope.launch {
//            _settingList.value = settingRepository.getAllSetting().first()
//        }
//    }
}