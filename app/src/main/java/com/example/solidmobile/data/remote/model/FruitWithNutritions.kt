package com.example.solidmobile.data.remote.model

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions

data class FruitWithNutritions(
    val fruit: Fruit,
    val nutritions: Nutritions
)