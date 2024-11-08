package com.bajiguri.bandrek.screen.RomScreen

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.Setting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RomViewModel @Inject constructor(
    private val romRepository: RomRepository
) : ViewModel() {

    fun getRomList(platformCode: String): StateFlow<List<Rom>> {
        return romRepository.getAllRom(platformCode)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

}
