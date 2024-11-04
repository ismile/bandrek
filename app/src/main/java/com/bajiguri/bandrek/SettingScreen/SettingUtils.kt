package com.bajiguri.bandrek.SettingScreen

import android.content.Intent
import androidx.core.app.ActivityCompat

val REQUEST_CHOOSE_FOLDER = 1;

fun openDirectory() {
    // Choose a directory using the system's file picker.
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when it loads.
    }

//    ActivityCompat.startActivityForResult(intent, REQUEST_CHOOSE_FOLDER);
}