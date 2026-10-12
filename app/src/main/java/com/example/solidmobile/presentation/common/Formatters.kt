package com.example.solidmobile.presentation.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
private val ruDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale("ru"))

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toRuDateText(): String = this.format(ruDateFormatter)

fun Double.toCaloriesText(): String {
    val valueText = if (this % 1.0 == 0.0) {
        this.toInt().toString()
    } else {
        String.format(Locale.US, "%.1f", this)
    }
    return "$valueText ккал"
}