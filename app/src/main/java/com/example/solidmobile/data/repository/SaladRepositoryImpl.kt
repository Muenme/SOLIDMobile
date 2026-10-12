package com.example.solidmobile.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.model.FruitSaladComposition
import com.example.solidmobile.domain.repository.SaladRepository
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaladRepositoryImpl @Inject constructor() : SaladRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    private val salads = mutableListOf(
        FruitSalad(id = 1, name = "Тестовый салат", calories = 0.0, dateUpdate = LocalDateTime.now())
    )

    private val composition = mutableListOf(
        FruitSaladComposition(id = 1, fruitId = 1, saladId = 1)
    )

    // Счётчики id — отдельно для каждой "таблицы", как в реальной БД с автоинкрементом.
    // Это чинит баг с хардкодом id=1 в оригинальном addToSalad().
    @RequiresApi(Build.VERSION_CODES.O)
    private val saladIdCounter = AtomicInteger(salads.maxOf { it.id })
    private val compositionIdCounter = AtomicInteger(composition.maxOf { it.id })

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllSalads(): List<FruitSalad> {
        delay(300)
        return salads.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSaladById(saladId: Int): FruitSalad? {
        delay(150)
        return salads.firstOrNull { it.id == saladId }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createSalad(name: String): Int {
        delay(200)
        val newId = saladIdCounter.incrementAndGet()
        salads.add(
            FruitSalad(id = newId, name = name, calories = 0.0, dateUpdate = LocalDateTime.now())
        )
        return newId
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateSaladName(saladId: Int, name: String) {
        val index = salads.indexOfFirst { it.id == saladId }
        if (index == -1) return
        salads[index] = salads[index].copy(name = name, dateUpdate = LocalDateTime.now())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateSaladCalories(saladId: Int, calories: Double) {
        val index = salads.indexOfFirst { it.id == saladId }
        if (index == -1) return
        salads[index] = salads[index].copy(calories = calories, dateUpdate = LocalDateTime.now())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun deleteSalad(saladId: Int) {
        salads.removeAll { it.id == saladId }
        composition.removeAll { it.saladId == saladId }
    }

    override suspend fun getFruitIdsForSalad(saladId: Int): List<Int> {
        return composition.filter { it.saladId == saladId }.map { it.fruitId }
    }

    override suspend fun addFruitToSalad(saladId: Int, fruitId: Int) {
        val alreadyAdded = composition.any {
            it.saladId == saladId && it.fruitId == fruitId
        }
        if (alreadyAdded) return

        composition.add(
            FruitSaladComposition(
                id = compositionIdCounter.incrementAndGet(),
                fruitId = fruitId,
                saladId = saladId
            )
        )
    }

    override suspend fun removeFruitFromSalad(saladId: Int, fruitId: Int) {
        composition.removeAll {
            it.saladId == saladId && it.fruitId == fruitId
        }
    }
}