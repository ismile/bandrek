package com.bajiguri.bandrek.screen.RomScreen

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

    fun getAllRom(platformCode: String): Flow<List<Rom>> {
        return appDao.getAllRomByPlatformCode(platformCode)
    }
}
