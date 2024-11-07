package com.bajiguri.bandrek.screen.PlatformScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Setting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlatformViewModel @Inject constructor(
    private val platformRepository: PlatformRepository
) : ViewModel() {

    val platformList: StateFlow<List<Platform>> = platformRepository.getAllPlatform()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Optional: Stop collecting after 5 seconds of inactivity
            initialValue = emptyList()
        )

}
