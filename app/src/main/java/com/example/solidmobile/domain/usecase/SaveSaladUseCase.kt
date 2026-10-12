package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.repository.SaladRepository
import javax.inject.Inject

class SaveSaladUseCase @Inject constructor(
    private val saladRepository: SaladRepository,
    private val calculateCalories: CalculateSaladCaloriesUseCase,
    private val fruitRepository: com.example.solidmobile.domain.repository.FruitRepository
) {
    suspend operator fun invoke(
        saladId: Int?,
        name: String,
        fruitIds: List<Int>
    ): Int {
        val id = saladId ?: saladRepository.createSalad(name)

        if (saladId != null) {
            saladRepository.updateSaladName(id, name)

            val currentFruitIds = saladRepository.getFruitIdsForSalad(id)
            (currentFruitIds - fruitIds.toSet()).forEach {
                saladRepository.removeFruitFromSalad(id, it)
            }
        }

        (fruitIds - saladRepository.getFruitIdsForSalad(id).toSet()).forEach {
            saladRepository.addFruitToSalad(id, it)
        }

        val fruits = fruitIds.mapNotNull { fruitRepository.getFruitById(it) }
        val calories = calculateCalories(fruits)
        saladRepository.updateSaladCalories(id, calories)

        return id
    }
}