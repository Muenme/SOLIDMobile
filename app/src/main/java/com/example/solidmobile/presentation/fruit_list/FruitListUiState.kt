package com.example.solidmobile.presentation.fruit_list

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.usecase.SortOrderFruitByName

data class FruitListUiState(
    val fruits: List<Fruit> = emptyList(),
    val isLoading: Boolean = false,
    val sortOrder: SortOrderFruitByName = SortOrderFruitByName.NAME
)