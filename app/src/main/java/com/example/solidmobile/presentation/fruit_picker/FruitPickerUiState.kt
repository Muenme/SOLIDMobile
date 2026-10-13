package com.example.solidmobile.presentation.fruit_picker

import com.example.solidmobile.domain.model.Fruit

data class FruitPickerUiState(
    val isLoading: Boolean = false,
    val fruits: List<Fruit> = emptyList(),
    val errorMessage: String? = null
)