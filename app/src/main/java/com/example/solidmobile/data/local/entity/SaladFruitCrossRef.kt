package com.example.solidmobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Связующая таблица многие ко многим, замена СompositionFruitSalad
// Составной первичный ключ не даёт добавить один и тот же фрукт дважды
@Entity(
    tableName = "salad_fruit_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = FruitSaladEntity::class,
            parentColumns = ["id"],
            childColumns = ["saladId"],
            onDelete = ForeignKey.CASCADE
        )
        // ForeignKey на FruitEntity убран — фрукт теперь внешний ресурс
    ],
    indices = [
        Index("saladId"),
        Index("fruitId") // оставляем индекс для скорости выборки, просто не FK
    ]
)
data class SaladFruitCrossRef(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val saladId: Int,
    val fruitId: Int
)