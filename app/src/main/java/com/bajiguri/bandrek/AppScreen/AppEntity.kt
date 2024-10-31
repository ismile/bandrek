package com.bajiguri.bandrek.AppScreen

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.painter.Painter
import coil3.compose.AsyncImagePainter

data class AppInfo(
    var name: String? = null,
    var activityName: String,
    var packageName: String = "",
    var drawable: Drawable?,
    var icon: Bitmap? = null,
    var iconBitmap: Painter? = null,
    val appType: Int = 0,
)