// data/repository/SaladRepositoryImpl.kt
package com.example.solidmobile.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.solidmobile.data.local.dao.SaladDao
import com.example.solidmobile.data.local.entity.FruitSaladEntity
import com.example.solidmobile.data.local.entity.SaladFruitCrossRef
import com.example.solidmobile.data.local.toDomain
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.repository.SaladRepository
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaladRepositoryImpl @Inject constructor(
    private val saladDao: SaladDao
) : SaladRepository {

    override suspend fun getAllSalads(): List<FruitSalad> =
        saladDao.getAllSalads().map { it.toDomain() }

    override suspend fun getSaladById(saladId: Int): FruitSalad? =
        saladDao.getSaladById(saladId)?.toDomain()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createSalad(name: String): Int {
        val entity = FruitSaladEntity(
            id = 0,                       // Room сам сгенерирует id
            name = name,
            calories = 0.0,
            dateUpdate = LocalDateTime.now()
        )
        return saladDao.insertSalad(entity).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateSaladName(saladId: Int, name: String) {
        val current = saladDao.getSaladById(saladId) ?: return
        saladDao.updateSalad(current.copy(name = name, dateUpdate = LocalDateTime.now()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateSaladCalories(saladId: Int, calories: Double) {
        val current = saladDao.getSaladById(saladId) ?: return
        saladDao.updateSalad(current.copy(calories = calories, dateUpdate = LocalDateTime.now()))
    }

    override suspend fun deleteSalad(saladId: Int) {
        saladDao.deleteSaladById(saladId)   // связи в cross_ref удалятся каскадом
    }

    override suspend fun getFruitIdsForSalad(saladId: Int): List<Int> =
        saladDao.getFruitIdsForSalad(saladId)

    override suspend fun addFruitToSalad(saladId: Int, fruitId: Int) {
        saladDao.addFruitToSalad(
            SaladFruitCrossRef(
                saladId = saladId,
                fruitId = fruitId
            )
        )
    }
    override suspend fun removeFruitFromSalad(saladId: Int, fruitId: Int) {
        saladDao.removeFruitFromSalad(saladId, fruitId)
    }
}