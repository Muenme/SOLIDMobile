package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.repository.FruitRepository
import com.example.solidmobile.domain.repository.SaladRepository
import javax.inject.Inject

class GetSortedByCaloriesSaladsUseCase @Inject constructor(
    private val repository: SaladRepository
){
    suspend operator fun invoke(sortBy: SortOrderSaladByCalories): List<FruitSalad>{
        val salads = repository.getAllSalads()
        return when (sortBy){
            SortOrderSaladByCalories.CALORIES -> salads.sortedBy { it.calories }
        }
    }
}
enum class SortOrderSaladByCalories { CALORIES }