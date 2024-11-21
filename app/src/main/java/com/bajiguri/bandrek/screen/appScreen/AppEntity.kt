package com.bajiguri.bandrek.screen.appScreen

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.painter.Painter

data class AppInfo(
    var name: String? = null,
    var activityName: String = "",
    var packageName: String = "",
    var drawable: Drawable? = null,
    var icon: Bitmap? = null,
    var iconBitmap: Painter? = null,
    val appType: Int = 0,
)