package com.bajiguri.bandrek.screen.PlatformScreen

import com.bajiguri.bandrek.AppDao
import com.bajiguri.bandrek.Platform
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlatformRepository @Inject constructor(
    private val appDao: AppDao
) {
    fun getAllPlatform(): Flow<List<Platform>> {
        return appDao.getAllPlatform()
    }
}
