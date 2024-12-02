package com.bajiguri.bandrek.screen.settingScreen

import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Setting
import com.bajiguri.bandrek.external.iddb.IddbClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    private val iddbClient: IddbClient
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

    suspend fun scan(platform: Platform, updateProgres: (Float) -> Unit) {
        val rom = settingRepository.getAllRomByPlatformCodeDirect(platform.code)
        val total = rom.size

        rom.forEachIndexed { i, it ->
            val response = iddbClient.searchRomByName(it.name.substringBefore(".").substringBefore("(").replace("_", " "), platform.code)
            if(response.isNotEmpty()) {
                val first = response.first()
                val d = it.copy(
                    name = first.name,
                    description = first.summary.orEmpty(),
                    genres = first.genres.map { it.name }.joinToString(","),
                    rating = first.rating,
                    coverUrl = "https:"+first.cover.url.replace("t_thumb", "t_cover_big"),
                    artworkUrl = "https:"+first.artworks?.first()?.url?.replace("t_thumb", "t_1080p"),
                )
                settingRepository.upsertRom(d)
            }
            updateProgres((i+1).toFloat()/total)
        }
    }
}