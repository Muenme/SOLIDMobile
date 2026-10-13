package com.example.solidmobile.data.local

import com.example.solidmobile.data.local.entity.FruitEntity
import com.example.solidmobile.data.local.entity.FruitSaladEntity
import com.example.solidmobile.data.local.entity.NutritionsEntity
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.model.Nutritions

fun FruitEntity.toDomain() = Fruit(
    id = id, genus = genus, name = name,
    family = family, order = order, nutritionsId = nutritionsId
)

fun NutritionsEntity.toDomain() = Nutritions(
    id = id, carbohydrates = carbohydrates, protein = protein,
    fat = fat, calories = calories, sugar = sugar
)

fun FruitSaladEntity.toDomain() = FruitSalad(
    id = id, name = name, calories = calories, dateUpdate = dateUpdate
)