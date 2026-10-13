package com.example.solidmobile.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromEpochSeconds(value: Long?): LocalDateTime? =
        value?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toEpochSeconds(date: LocalDateTime?): Long? =
        date?.toEpochSecond(ZoneOffset.UTC)
}