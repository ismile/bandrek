package com.bajiguri.bandrek.utils

import com.bajiguri.bandrek.Platform

val PSX_CODE = "psx";
val GBA_CODE = "gba";
val NDS_CODE = "nds";
val N3DS_CODE = "3ds";
val PSP_CODE = "psp";
val PS2_CODE = "ps2";
val SWITCH_CODE = "switch";
val ANDROID_CODE = "android"

val PSX_PLATFORM = Platform(
    code = PSX_CODE,
    name = "Sony Playstation",
    fileExtension = "iso,chd,bin",
    appArgument = null,
    activityName = null
)

val GBA_PLATFORM = Platform(
    code = GBA_CODE,
    name = "Game boy Advanced",
    fileExtension = "gba,zip",
    appArgument = null,
    activityName = null
)

val NDS_PLATFORM = Platform(
    code = NDS_CODE,
    name = "Nintendo DS",
    fileExtension = "nds,zip",
    appArgument = null,
    activityName = null
)

val N3DS_PLATFORM = Platform(
    code = N3DS_CODE,
    name = "Nintendo 3DS",
    fileExtension = "3ds,cxi",
    appArgument = null,
    activityName = null
)

val PSP_PLATFORM = Platform(
    code = PSP_CODE,
    name = "Playstation Portable",
    fileExtension = "chd,iso",
    appArgument = null,
    activityName = null
)

val PS2_PLATFORM = Platform(
    code = PS2_CODE,
    name = "Playstation 2",
    fileExtension = "chd,iso",
    appArgument = null,
    activityName = null
)

val SWITCH_PLATFORM = Platform(
    code = SWITCH_CODE,
    name = "Nintendo Switch",
    fileExtension = "nsp,xci",
    appArgument = null,
    activityName = null
)

val ANDROID_PLATFORM = Platform(
    code = ANDROID_CODE,
    name = "ANDROID",
    fileExtension = "apk",
    appArgument = null,
    activityName = null
)

val platformMap = mapOf(PSX_CODE to PSX_PLATFORM, GBA_CODE to GBA_PLATFORM, NDS_CODE to NDS_PLATFORM, N3DS_CODE to N3DS_PLATFORM, PSP_CODE to PSP_PLATFORM, PS2_CODE to PS2_PLATFORM, SWITCH_CODE to SWITCH_PLATFORM)