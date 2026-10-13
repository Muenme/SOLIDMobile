// data/repository/FruitRepositoryImpl.kt
package com.example.solidmobile.data.repository

import com.example.solidmobile.data.local.dao.FruitDao
import com.example.solidmobile.data.local.entity.FruitEntity
import com.example.solidmobile.data.local.entity.NutritionsEntity
import com.example.solidmobile.data.local.toDomain
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.repository.FruitRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FruitRepositoryImpl @Inject constructor(
    private val fruitDao: FruitDao
) : FruitRepository {

    // Таблицы фруктов/нутриентов - статичный справочник
    // Заполняем один раз при первом обращении, если БД ещё пуста
    private val seedMutex = Mutex()
    private var isSeeded = false

    private suspend fun ensureSeeded() {
        if (isSeeded) return
        seedMutex.withLock {
            if (isSeeded) return@withLock
            if (fruitDao.getAllFruits().isEmpty()) {
                fruitDao.insertNutritions(defaultNutritions)
                fruitDao.insertFruits(defaultFruits)
            }
            isSeeded = true
        }
    }

    override suspend fun getAllFruits(): List<Fruit> {
        ensureSeeded()
        return fruitDao.getAllFruits().map { it.toDomain() }
    }

    override suspend fun getFruitById(id: Int): Fruit? {
        ensureSeeded()
        return fruitDao.getFruitById(id)?.toDomain()
    }

    override suspend fun getAllNutritions(): List<Nutritions> {
        ensureSeeded()
        return fruitDao.getAllNutritions().map { it.toDomain() }
    }

    override suspend fun getNutritionsById(id: Int): Nutritions? {
        ensureSeeded()
        return fruitDao.getNutritionsById(id)?.toDomain()
    }

    private companion object {
        val defaultNutritions = listOf(
            NutritionsEntity(1, carbohydrates = 5.5, protein = 0.8, fat = 0.4, calories = 29.0, sugar = 5.4),
            NutritionsEntity(2, carbohydrates = 22.0, protein = 1.0, fat = 0.2, calories = 96.0, sugar = 17.2),
            NutritionsEntity(3, carbohydrates = 15.0, protein = 0.4, fat = 0.1, calories = 57.0, sugar = 10.0)
        )

        val defaultFruits = listOf(
            FruitEntity(1, genus = "Fragaria", name = "Клубника", family = "Rosaceae", order = "Rosales", nutritionsId = 1),
            FruitEntity(2, genus = "Musa", name = "Банан", family = "Musaceae", order = "Zingiberales", nutritionsId = 2),
            FruitEntity(3, genus = "Pyrus", name = "Груша", family = "Rosaceae", order = "Rosales", nutritionsId = 3)
        )
    }
}