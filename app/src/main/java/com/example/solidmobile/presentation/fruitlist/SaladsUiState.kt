package com.example.solidmobile.presentation.fruitlist

import com.example.solidmobile.domain.model.FruitSalad

data class SaladsUiState(
    val isLoading: Boolean = false,
    val salads: List<FruitSalad> = emptyList(),
    val errorMessage: String? = null
)