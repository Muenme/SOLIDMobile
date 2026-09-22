package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.repository.FruitRepository

class GetSortedByCaloriesSaladsUseCase (
    private val repository: FruitRepository
){
    suspend operator fun invoke(sortBy: SortOrderSaladByCalories): List<FruitSalad>{
        val salads = repository.getSalad()
        return when (sortBy){
            SortOrderSaladByCalories.CALORIES -> salads.sortedBy { it.calories }
        }
    }
}
enum class SortOrderSaladByCalories { CALORIES }