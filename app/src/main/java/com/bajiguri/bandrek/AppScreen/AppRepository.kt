package com.bajiguri.bandrek.AppScreen

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

data class AppInfo(
    val name: String,
    val packageName: String,
    val icon: Int
)

class AppRepository @Inject constructor() {
    @Inject
    @ApplicationContext
    lateinit var context: Context

    fun getInstalledApps(): List<AppInfo> {
        val pm = context.packageManager
        return pm
            .getInstalledApplications(PackageManager.GET_META_DATA)
            .map { appInfo ->
                AppInfo(
                    appInfo.loadLabel(pm).toString(),
                    appInfo.packageName,
                    appInfo.icon
                )
            }
    }
}
