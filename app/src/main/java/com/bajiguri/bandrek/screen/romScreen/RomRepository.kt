package com.bajiguri.bandrek.screen.romScreen

import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Rom
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RomRepository @Inject constructor(
    private val appDao: AppDao
) {
    fun getAllRom(): Flow<List<Rom>> {
        return appDao.getAllRom()
    }

    fun getAllPlatform(): Flow<List<Platform>> {
        return appDao.getAllPlatform()
    }

    fun getAllRom(platformCode: String): Flow<List<Rom>> {
        return appDao.getAllRomByPlatformCode(platformCode)
    }
}
