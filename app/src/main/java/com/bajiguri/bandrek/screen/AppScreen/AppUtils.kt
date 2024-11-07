package com.bajiguri.bandrek.screen.AppScreen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun startApp(context: Context, app: AppInfo) {
    try {
//        val intent = Intent()
//        intent.component = ComponentName(app.name!!, app.activityName!!)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(intent)

        val intent = context.packageManager.getLaunchIntentForPackage(app.packageName) ?: return
        context.startActivity(intent)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun goAppDetail(context: Context, app: AppInfo) {
    try {
        val intent = Intent()
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", app.name, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun goAppDelete(context: Context, app: AppInfo) {
    try {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.setData(Uri.fromParts("package", app.name, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}