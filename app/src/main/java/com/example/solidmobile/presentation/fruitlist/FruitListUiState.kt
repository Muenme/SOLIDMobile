package com.example.solidmobile.presentation.fruitlist

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.usecase.SortOrderFruitByName

// Состояние ЭКРАНА, а не бизнес-модель. Знает про загрузку и ошибки —
// доменная Movie про такое знать не должна.
data class FruitListUiState(
    val fruits: List<Fruit> = emptyList(),
    val isLoading: Boolean = false,
    val sortOrder: SortOrderFruitByName = SortOrderFruitByName.NAME
)