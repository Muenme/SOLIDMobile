package com.example.solidmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fruits")
data class FruitEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val genus: String,
    val name: String,
    val family: String,
    val order: String,
    val nutritionsId: Int
)