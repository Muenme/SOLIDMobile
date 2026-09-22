package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.repository.FruitRepository

class GetSortedByNameSaladsUseCase (
    private val repository: FruitRepository
){
    suspend operator fun invoke(sortBy: SortOrderSaladByName): List<FruitSalad>{
        val salads = repository.getSalad()
        return when (sortBy){
            SortOrderSaladByName.NAME -> salads.sortedBy { it.name }
        }
    }
}
enum class SortOrderSaladByName { NAME }