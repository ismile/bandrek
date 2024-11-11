package com.bajiguri.bandrek.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.bajiguri.bandrek.Platform
import com.bajiguri.bandrek.Rom

val PSX_PLATFORM = Platform(
    code = "psx",
    name = "Sony Playstation",
    fileExtension = "iso,chd,bin",
    appArgument = null,
    activityName = null
)

val GBA_PLATFORM = Platform(
    code = "gba",
    name = "Game boy Advanced",
    fileExtension = "gba,zip",
    appArgument = null,
    activityName = null
)

val NDS_PLATFORM = Platform(
    code = "nds",
    name = "Nintendo DS",
    fileExtension = "nds,zip",
    appArgument = null,
    activityName = null
)

val N3DS_PLATFORM = Platform(
    code = "3ds",
    name = "Nintendo 3DS",
    fileExtension = "3ds,cxi",
    appArgument = null,
    activityName = null
)

val PSP_PLATFORM = Platform(
    code = "psp",
    name = "Playstation Portable",
    fileExtension = "chd,iso",
    appArgument = null,
    activityName = null
)

val PS2_PLATFORM = Platform(
    code = "ps2",
    name = "Playstation 2",
    fileExtension = "chd,iso",
    appArgument = null,
    activityName = null
)

val SWITCH_PLATFORM = Platform(
    code = "switch",
    name = "Nintendo Switch",
    fileExtension = "nsp,xci",
    appArgument = null,
    activityName = null
)

fun launchDuckStation(rom: Rom, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.component = ComponentName("com.github.stenzek.duckstation", "com.github.stenzek.duckstation.EmulationActivity")
    intent.putExtra("bootPath", rom.locationUri)
    intent.putExtra("resumeState", 0)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    context.startActivity(intent)
}