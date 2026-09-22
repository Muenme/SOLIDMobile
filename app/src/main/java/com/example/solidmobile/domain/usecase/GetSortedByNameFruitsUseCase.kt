package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.repository.FruitRepository

class GetSortedByNameFruitsUseCase (
    private val repository: FruitRepository
){
    suspend operator fun invoke(sortBy: SortOrderFruitByName): List<Fruit>{
        val fruits = repository.getFruit()
        return when (sortBy){
            SortOrderFruitByName.CALORIES -> fruits.sortedBy { it.nutritions.calories }
            SortOrderFruitByName.NAME -> fruits.sortedBy { it.name }
        }
    }
}
enum class SortOrderFruitByName { CALORIES, NAME }