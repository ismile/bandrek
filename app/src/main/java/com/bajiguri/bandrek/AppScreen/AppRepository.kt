package com.bajiguri.bandrek.AppScreen

import android.content.Context
import android.content.Intent
import com.bajiguri.bandrek.utils.getBitmapFromDrawable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppRepository @Inject constructor() {
    @Inject
    @ApplicationContext
    lateinit var context: Context

    fun getInstalledApps(): List<AppInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pm = context.packageManager

        return pm.queryIntentActivities(intent, 0).map {
            val drawable = it.activityInfo.loadIcon(pm)
            AppInfo(
                name = it.loadLabel(pm).toString(),
                activityName = it.activityInfo.name,
                packageName = it.activityInfo.packageName,
                drawable = drawable,
                icon = getBitmapFromDrawable(drawable!!)
            )

        }
    }
}
