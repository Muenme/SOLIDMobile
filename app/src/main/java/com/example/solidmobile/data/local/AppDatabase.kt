package com.example.solidmobile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.solidmobile.data.local.dao.FruitDao
import com.example.solidmobile.data.local.dao.SaladDao
import com.example.solidmobile.data.local.entity.FruitEntity
import com.example.solidmobile.data.local.entity.FruitSaladEntity
import com.example.solidmobile.data.local.entity.NutritionsEntity
import com.example.solidmobile.data.local.entity.SaladFruitCrossRef

@Database(
    entities = [
        FruitEntity::class,
        NutritionsEntity::class,
        FruitSaladEntity::class,
        SaladFruitCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fruitDao(): FruitDao
    abstract fun saladDao(): SaladDao
}