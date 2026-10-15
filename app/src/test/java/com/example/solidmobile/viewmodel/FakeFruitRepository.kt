package com.example.solidmobile.viewmodel

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.repository.FruitRepository
import kotlinx.coroutines.CompletableDeferred

class FakeFruitRepository : FruitRepository {

    private var fruits = listOf<Fruit>()
    private var nutritionsMap = mapOf<Int, Nutritions>()
    var shouldThrow = false
    var exceptionToThrow: Exception = RuntimeException("fake failure")

    var deferredFruits: CompletableDeferred<List<Fruit>>? = null

    fun setFruits(list: List<Fruit>) {
        fruits = list
    }

    override suspend fun getAllFruits(): List<Fruit> {
        if (shouldThrow) throw exceptionToThrow
        return fruits
    }

    override suspend fun getFruitById(id: Int): Fruit? =
        fruits.find { it.id == id }

    override suspend fun getAllNutritions(): List<Nutritions> =
        nutritionsMap.values.toList()

    override suspend fun getNutritionsById(id: Int): Nutritions? =
        nutritionsMap[id]
}