package com.bajiguri.bandrek.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.bajiguri.bandrek.Rom

val psxPlayer = mapOf("duckstation" to fun(rom: Rom, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.component = ComponentName(
        "com.github.stenzek.duckstation",
        "com.github.stenzek.duckstation.EmulationActivity"
    )
    intent.putExtra("bootPath", rom.locationUri)
    intent.putExtra("resumeState", 0)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    context.startActivity(intent)
})

val gbaPlayer = mapOf("pizzaboypro" to fun(rom: Rom, context: Context) {
    val intent = Intent().apply {
        component =
            ComponentName("it.dbtecno.pizzaboygbapro", "it.dbtecno.pizzaboygbapro.MainActivity")
        putExtra("rom_uri", rom.locationUri)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    context.startActivity(intent)
})

val n3dsPlayer = mapOf("citra" to fun(rom: Rom, context: Context) {
    val intent = Intent().apply {
        component = ComponentName("org.citra.citra_emu", "org.citra.citra_emu.ui.EmulationActivity")
        action = Intent.ACTION_VIEW
        putExtra("GamePath", rom.locationUri)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    context.startActivity(intent)
})

val pspPlayer = mapOf("ppsspp" to fun(rom: Rom, context: Context) {
    val intent = Intent().apply {
        component = ComponentName("org.ppsspp.ppsspp", "org.ppsspp.ppsspp.PpssppActivity")
        action = Intent.ACTION_VIEW
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse(rom.locationUri)
        type = "application/octet-stream"
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY)
    }
    context.startActivity(intent)
})

val switchPlayer = mapOf("sudachi" to fun(rom: Rom, context: Context) {
    val intent = Intent().apply {
        component = ComponentName(
            "org.sudachi.sudachi_emu.ea",
            "org.sudachi.sudachi_emu.activities.EmulationActivity"
        )
        action = Intent.ACTION_VIEW // or ACTION_TECH_DISCOVERED
        data = Uri.parse(rom.locationUri)
        // addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Optional: If needed
    }
    context.startActivity(intent)
})

val ps2Player = mapOf("neathersx" to fun(rom: Rom, context: Context) {
    val intent = Intent().apply {
        component =
            ComponentName("xyz.aethersx2.android", "xyz.aethersx2.android.EmulationActivity")
        action = Intent.ACTION_MAIN
        putExtra("bootPath", rom.locationUri) // Pass file URI as string
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    context.startActivity(intent)
})

val ndsPlayer = mapOf("drastic" to fun(rom: Rom, context: Context) {
    val intent = Intent().apply {
        component = ComponentName("com.dsemu.drastic", "com.dsemu.drastic.DraSticActivity")
        action = Intent.ACTION_VIEW
        data = Uri.parse(rom.locationUri)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    context.startActivity(intent)
})

val playerMap = mapOf(PSX_CODE to psxPlayer, GBA_CODE to gbaPlayer, N3DS_CODE to n3dsPlayer, PSP_CODE to pspPlayer, SWITCH_CODE to switchPlayer, PS2_CODE to ps2Player, NDS_CODE to ndsPlayer)