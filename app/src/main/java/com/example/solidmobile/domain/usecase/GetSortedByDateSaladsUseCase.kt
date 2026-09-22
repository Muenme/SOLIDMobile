package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.repository.FruitRepository

class GetSortedByDateSaladsUseCase (
    private val repository: FruitRepository
){
    suspend operator fun invoke(sortBy: SortOrderSaladByDate): List<FruitSalad>{
        val salads = repository.getSalad()
        return when (sortBy){
            SortOrderSaladByDate.DATE -> salads.sortedBy { it.dateUpdate }
        }
    }
}
enum class SortOrderSaladByDate { DATE }