package com.bajiguri.bandrek.screen.AppScreen

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.utils.ANDROID_PLATFORM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    private val _appList = MutableStateFlow<List<AppInfo>>(emptyList())
    val appList: StateFlow<List<AppInfo>> = _appList.asStateFlow()

    fun loadApps() {
        viewModelScope.launch {
            _appList.value = repository.getInstalledApps().sortedBy { it.name }
        }
    }

    suspend fun addToGameLibrary(app: AppInfo) {
        repository.upsertRom(Rom(
            code = app.name+"_"+app.packageName,
            platformCode = ANDROID_PLATFORM.code,
            name = app.name.orEmpty(),
            description = "",
            category = "",
            packageName = app.packageName,
            activityName = app.activityName
        ))
    }

    init {
        loadApps()
        viewModelScope.launch {
            repository.addAndroidPlatform()
        }
    }
}