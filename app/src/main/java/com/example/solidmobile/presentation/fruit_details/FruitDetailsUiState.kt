package com.example.solidmobile.presentation.fruit_details

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions

data class FruitDetailsUiState(
    val isLoading: Boolean = false,
    val fruit: Fruit? = null,
    val nutritions: Nutritions? = null,
    val errorMessage: String? = null
)