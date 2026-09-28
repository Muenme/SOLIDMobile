package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.repository.FruitRepository
import javax.inject.Inject

class GetSortedByDateSaladsUseCase @Inject constructor(
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