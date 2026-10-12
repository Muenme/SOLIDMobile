package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.repository.FruitRepository
import com.example.solidmobile.domain.repository.SaladRepository
import javax.inject.Inject

data class SaladWithFruits(
    val salad: FruitSalad,
    val fruits: List<Fruit>
)

class GetSaladWithFruitsUseCase @Inject constructor(
    private val saladRepository: SaladRepository,
    private val fruitRepository: FruitRepository
) {
    suspend operator fun invoke(saladId: Int): SaladWithFruits? {
        val salad = saladRepository.getSaladById(saladId) ?: return null
        val fruitIds = saladRepository.getFruitIdsForSalad(saladId)
        val fruits = fruitIds.mapNotNull { fruitRepository.getFruitById(it) }
        return SaladWithFruits(salad, fruits)
    }
}