package com.example.solidmobile.presentation.salads

import com.example.solidmobile.domain.model.FruitSalad

sealed interface SaladsUiState {
    data object Loading : SaladsUiState
    data class Error(val message: String) : SaladsUiState
    data class Success(val salads: List<FruitSalad>) : SaladsUiState
}