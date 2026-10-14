package com.example.solidmobile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.solidmobile.data.local.dao.SaladDao
import com.example.solidmobile.data.local.entity.FruitSaladEntity
import com.example.solidmobile.data.local.entity.NutritionsEntity
import com.example.solidmobile.data.local.entity.SaladFruitCrossRef

@Database(
    entities = [
        FruitSaladEntity::class,
        SaladFruitCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun saladDao(): SaladDao
}