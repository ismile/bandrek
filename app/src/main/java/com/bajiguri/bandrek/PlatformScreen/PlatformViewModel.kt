package com.bajiguri.bandrek.PlatformScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Platform
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PlatformViewModel @Inject constructor(
    private val appDao: AppDao
) : ViewModel() {

    val platformList: StateFlow<List<Platform>> = appDao.getAllPlatform()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Optional: Stop collecting after 5 seconds of inactivity
            initialValue = emptyList()
        )

//    private val _platformList = MutableStateFlow<List<Platform>>(emptyList())
//    val platformList: StateFlow<List<Platform>> = _platformList.asStateFlow()

//    fun refreshPlatforms() {
//        viewModelScope.launch {
//            appDao.getAllPlatform().collect { platforms ->
//                _platformList.value = platforms
//            }
//        }
//    }

    suspend fun addPlatform(platform: Platform) {
        appDao.upsertPlatform(platform)
//        refreshPlatforms()
    }

//    init {
//        viewModelScope.launch {
//            _platformList.value = appDao.getAllPlatform().first()
//        }
//    }
}
