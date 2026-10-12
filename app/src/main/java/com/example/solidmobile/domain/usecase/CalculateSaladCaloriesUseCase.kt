package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.repository.FruitRepository
import javax.inject.Inject

class CalculateSaladCaloriesUseCase @Inject constructor(
    private val fruitRepository: FruitRepository
) {
    suspend operator fun invoke(fruits: List<Fruit>): Double {
        return fruits.sumOf { fruit ->
            fruitRepository.getNutritionsById(fruit.nutritionsId)?.calories ?: 0.0
        }
    }
}