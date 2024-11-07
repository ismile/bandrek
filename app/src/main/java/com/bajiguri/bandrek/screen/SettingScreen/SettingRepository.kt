package com.bajiguri.bandrek.screen.SettingScreen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Setting
import com.bajiguri.bandrek.utils.GBA_PLATFORM
import com.bajiguri.bandrek.utils.N3DS_PLATFORM
import com.bajiguri.bandrek.utils.NDS_PLATFORM
import com.bajiguri.bandrek.utils.PS2_PLATFORM
import com.bajiguri.bandrek.utils.PSP_PLATFORM
import com.bajiguri.bandrek.utils.PSX_PLATFORM
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.File
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

    fun getDirectories(romDirectory: String): List<File>? {
        val dir = Environment.getExternalStorageDirectory().toString() + "/" + romDirectory
        val f = File(dir)
        return f.listFiles()?.filter { it.isDirectory }
    }

    suspend fun savePlatform(files: List<File>?) {
        files?.forEach {
            when (it.name) {
                "psx" -> {
                    appDao.upsertPlatform(PSX_PLATFORM)
                }

                "gba" -> {
                    appDao.upsertPlatform(GBA_PLATFORM)
                }

                "nds" -> {
                    appDao.upsertPlatform(NDS_PLATFORM)
                }

                "3ds" -> {
                    appDao.upsertPlatform(N3DS_PLATFORM)
                }

                "psp" -> {
                    appDao.upsertPlatform(PSP_PLATFORM)
                }

                "ps2" -> {
                    appDao.upsertPlatform(PS2_PLATFORM)
                }
            }
        }
    }
}
