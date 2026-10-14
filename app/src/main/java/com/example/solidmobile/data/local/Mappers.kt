package com.example.solidmobile.data.local

import com.example.solidmobile.data.local.entity.FruitSaladEntity
import com.example.solidmobile.data.local.entity.NutritionsEntity
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.model.Nutritions

fun NutritionsEntity.toDomain() = Nutritions(
    id = id, carbohydrates = carbohydrates, protein = protein,
    fat = fat, calories = calories, sugar = sugar
)

fun FruitSaladEntity.toDomain() = FruitSalad(
    id = id, name = name, calories = calories, dateUpdate = dateUpdate
)