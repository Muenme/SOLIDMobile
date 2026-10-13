package com.example.solidmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutritions")
data class NutritionsEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val calories: Double,
    val sugar: Double
)