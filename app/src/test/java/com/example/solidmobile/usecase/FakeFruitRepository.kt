package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.repository.FruitRepository

class FakeFruitRepository : FruitRepository {

    private var fruits = listOf<Fruit>()
    private var nutritionsMap = mapOf<Int, Nutritions>()

    // Для проверки, что use case действительно дергает репозиторий,
    // а не считает что-то "в уме"
    var getNutritionsCallCount = 0
        private set

    fun setFruits(list: List<Fruit>) {
        fruits = list
    }

    fun setNutritions(list: List<Nutritions>) {
        nutritionsMap = list.associateBy { it.id }
    }

    override suspend fun getAllFruits(): List<Fruit> = fruits

    override suspend fun getFruitById(id: Int): Fruit? =
        fruits.find { it.id == id }

    override suspend fun getAllNutritions(): List<Nutritions> =
        nutritionsMap.values.toList()

    override suspend fun getNutritionsById(id: Int): Nutritions? {
        getNutritionsCallCount++
        return nutritionsMap[id]
    }
}