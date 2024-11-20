package com.bajiguri.bandrek.screen.RomScreen

import androidx.activity.result.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.Setting
import com.bajiguri.bandrek.external.iddb.Game
import com.bajiguri.bandrek.external.iddb.IddbClient
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
    private val romRepository: RomRepository,
    private val iddbClient: IddbClient,
    private val appDao: AppDao
) : ViewModel() {

    private val _selectedRom = MutableStateFlow<Rom>(Rom())
    val selectedRom: StateFlow<Rom> = _selectedRom.asStateFlow()

    private val _showRomSheet = MutableStateFlow(false)
    val showRomSheet = _showRomSheet.asStateFlow()

    private val _showRomScannerSheet = MutableStateFlow(false)
    val showRomScannerSheet = _showRomScannerSheet.asStateFlow()

    fun getRomList(platformCode: String): StateFlow<List<Rom>> {
        return romRepository.getAllRom(platformCode)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun toggleRomSheet(value: Boolean) {
        _showRomSheet.value = value
    }

    fun toggleScannerSheet(value: Boolean) {
        _showRomScannerSheet.value = value
    }

    fun setSelectedRom(rom: Rom) {
        _selectedRom.value = rom
    }

    suspend fun searchByName(name: String, platformCode: String): List<Game> {
        return iddbClient.searchRomByName(name, platformCode)
    }

    suspend fun upsertRom(rom: Rom) {
        appDao.upsertRom(rom)
    }

}
