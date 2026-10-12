package com.example.solidmobile.presentation.salad_details
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.model.Nutritions

data class SaladDetailsUiState(
    val isLoading: Boolean = false,
    val salad: FruitSalad? = null,
    val fruits: List<Fruit> = emptyList(),
    val nutritionsByFruitId: Map<Int, Nutritions> = emptyMap(),
    val errorMessage: String? = null
)