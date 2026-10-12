package com.example.solidmobile.domain.repository

import com.example.solidmobile.domain.model.FruitSalad

interface SaladRepository {
    suspend fun getAllSalads(): List<FruitSalad>
    suspend fun getSaladById(id: Int): FruitSalad?
    suspend fun createSalad(name: String): Int          // возвращает id нового салата
    suspend fun updateSaladName(saladId: Int, name: String)
    suspend fun updateSaladCalories(saladId: Int, calories: Double)
    suspend fun deleteSalad(saladId: Int)

    suspend fun getFruitIdsForSalad(saladId: Int): List<Int>
    suspend fun addFruitToSalad(saladId: Int, fruitId: Int)
    suspend fun removeFruitFromSalad(saladId: Int, fruitId: Int)
}