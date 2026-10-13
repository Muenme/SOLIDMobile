package com.example.solidmobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

// Связующая таблица многие ко многим, замена СompositionFruitSalad
// Составной первичный ключ не даёт добавить один и тот же фрукт дважды
@Entity(
    tableName = "salad_fruit_cross_ref",
    primaryKeys = ["saladId", "fruitId"],
    foreignKeys = [
        ForeignKey(
            entity = FruitSaladEntity::class,
            parentColumns = ["id"],
            childColumns = ["saladId"],
            onDelete = ForeignKey.CASCADE   // удалили салат — связи удалятся сами
        ),
        ForeignKey(
            entity = FruitEntity::class,
            parentColumns = ["id"],
            childColumns = ["fruitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("saladId"), Index("fruitId")]
)
data class SaladFruitCrossRef(
    val saladId: Int,
    val fruitId: Int
)