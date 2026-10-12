package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.repository.FruitRepository
import javax.inject.Inject

class GetSortedByNameFruitsUseCase @Inject constructor(
    private val repository: FruitRepository
){
    suspend operator fun invoke(sortBy: SortOrderFruitByName): List<Fruit>{
        val fruits = repository.getAllFruits()
        return when (sortBy){
            SortOrderFruitByName.CALORIES ->
                {
                    val nutritionsById = repository.getAllNutritions().associateBy { it.id }

                    fruits.sortedBy { fruit ->
                        nutritionsById[fruit.nutritionsId]?.calories ?: Double.MAX_VALUE
                    }
                }
            SortOrderFruitByName.NAME -> fruits.sortedBy { it.name }
        }
    }
}
enum class SortOrderFruitByName { CALORIES, NAME }