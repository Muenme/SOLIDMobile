package com.example.solidmobile.data.repository

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.repository.FruitRepository
import jakarta.inject.Inject
import kotlinx.coroutines.delay

class FruitRepositoryImpl @Inject constructor(): FruitRepository{
    private val nutritions = listOf(
        Nutritions(1, carbohydrates = 5.5, protein = 0.8, fat = 0.4, calories = 29.0, sugar = 5.4),
        Nutritions(2, carbohydrates = 22.0, protein = 1.0, fat = 0.2, calories = 96.0, sugar = 17.2),
        Nutritions(3, carbohydrates = 15.0, protein = 0.4, fat = 0.1, calories = 57.0, sugar = 10.0)
    )

    private val fruits = listOf(
        Fruit(1, genus = "Fragaria", name = "Клубника", family = "Rosaceae", order = "Rosales", nutritionsId = 1),
        Fruit(2, genus = "Musa", name = "Банан", family = "Musaceae", order = "Zingiberales", nutritionsId = 2),
        Fruit(3, genus = "Pyrus", name = "Груша", family = "Rosaceae", order = "Rosales", nutritionsId = 3)
    )

    override suspend fun getAllFruits(): List<Fruit> {
        delay(300)
        return fruits
    }

    override suspend fun getFruitById(id: Int): Fruit? {
        delay(100)
        return fruits.firstOrNull { it.id == id }
    }

    override suspend fun getAllNutritions(): List<Nutritions> {
        return nutritions
    }

    override suspend fun getNutritionsById(id: Int): Nutritions? {
        return nutritions.firstOrNull { it.id == id }
    }
}