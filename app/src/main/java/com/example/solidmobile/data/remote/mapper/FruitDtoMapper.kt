package com.example.solidmobile.data.remote.mapper

import com.example.solidmobile.data.remote.dto.FruitDto
import com.example.solidmobile.data.remote.model.FruitWithNutritions
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions

fun FruitDto.toFruit(): Fruit = Fruit(
    id = id,
    genus = genus,
    name = name,
    family = family,
    order = order,
    nutritionsId = id // 1:1 связь, используем id фрукта как id его nutrition
)

fun FruitDto.toNutritions(): Nutritions = Nutritions(
    id = id, // тот же id, что и nutritionsId у Fruit
    carbohydrates = nutritions.carbohydrates,
    protein = nutritions.protein,
    fat = nutritions.fat,
    calories = nutritions.calories,
    sugar = nutritions.sugar
)

internal fun FruitDto.toFruitWithNutritions(): FruitWithNutritions =
    FruitWithNutritions(fruit = toFruit(), nutritions = toNutritions())