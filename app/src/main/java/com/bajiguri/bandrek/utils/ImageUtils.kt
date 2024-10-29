package com.bajiguri.bandrek.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

fun getBitmapFromDrawable(drawable: Drawable): Bitmap? {
    val image = drawable!!
    if (image is BitmapDrawable)
        return image?.bitmap
    else {
        var iWidth = image.intrinsicWidth
        var iHeight = image.intrinsicHeight
        if (iWidth < 0)
            iWidth = 1
        if (iHeight < 0)
            iHeight < 1
        val bmp = Bitmap.createBitmap(iWidth, iHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        image.setBounds(0, 0, canvas.width, canvas.height)
        image.draw(canvas)
        return bmp
    }
}