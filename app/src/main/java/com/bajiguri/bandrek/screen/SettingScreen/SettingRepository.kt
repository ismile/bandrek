package com.bajiguri.bandrek.screen.SettingScreen

import androidx.documentfile.provider.DocumentFile
import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Rom
import com.bajiguri.bandrek.Setting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepository @Inject constructor(
    private val appDao: AppDao
) {
    fun getAllSetting(): Flow<List<Setting>> {
        return appDao.getAllSetting()
    }

    suspend fun upsertSetting(data: Setting) {
        appDao.upsertSetting(data)
    }

    suspend fun upsertPlatform(data: Platform) {
        appDao.upsertPlatform(data)
    }

    suspend fun saveRom(files: List<DocumentFile>, platformCode: String) {
        files.forEach {
            appDao.upsertRom(
                Rom(
                    code = it.name.orEmpty(),
                    platformCode = platformCode,
                    name = it.name?.substringBeforeLast(".").orEmpty(),
                    description = "",
                    category = "",
                    genres = "",
                    locationUri = it.uri.toString(),
                    location = it.uri.path.toString(),
                    filename = it.name.orEmpty()
                )
            )
        }
    }
}
