package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.repository.FruitRepository

class GetSortedByNameFruitsUseCase (
    private val repository: FruitRepository
){
    suspend operator fun invoke(sortBy: SortOrderFruitByName): List<Fruit>{
        val fruits = repository.getFruit()
        return when (sortBy){
            SortOrderFruitByName.NAME -> fruits.sortedBy { it.name }
            SortOrderFruitByName.ORDER -> fruits.sortedBy { it.order }
            SortOrderFruitByName.FAMILY -> fruits.sortedBy { it.family }
        }
    }
}
enum class SortOrderFruitByName { NAME, ORDER, FAMILY}