package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.repository.FruitRepository
import javax.inject.Inject

class GetSortedByCaloriesFruitsUseCase @Inject constructor(
    private val repository: FruitRepository
){
    suspend operator fun invoke(sortBy: SortOrderFruitByCalories): List<Fruit>{
        val fruits = repository.getAllFruits()
        return when (sortBy){
            SortOrderFruitByCalories.NAME -> fruits.sortedBy { it.name }
            SortOrderFruitByCalories.ORDER -> fruits.sortedBy { it.order }
            SortOrderFruitByCalories.FAMILY -> fruits.sortedBy { it.family }
        }
    }
}
enum class SortOrderFruitByCalories { NAME, ORDER, FAMILY}