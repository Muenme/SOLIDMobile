package com.example.solidmobile.presentation.fruitlist
import com.example.solidmobile.domain.model.Fruit

data class SaladEditUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isEditing: Boolean = false,
    val saladId: Int? = null,
    val name: String = "",
    val selectedFruits: List<Fruit> = emptyList(),
    val totalCalories: Double = 0.0,
    val nameError: String? = null,
    val errorMessage: String? = null,
    val isSaved: Boolean = false,
    val savedSaladId: Int? = null
)