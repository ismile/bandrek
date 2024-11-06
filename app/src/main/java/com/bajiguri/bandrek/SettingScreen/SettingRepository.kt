package com.bajiguri.bandrek.SettingScreen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Setting
import dagger.hilt.android.qualifiers.ApplicationContext
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
}
