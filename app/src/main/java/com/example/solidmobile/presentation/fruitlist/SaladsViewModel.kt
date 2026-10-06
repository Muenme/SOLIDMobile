package com.example.solidmobile.presentation.fruitlist

import androidx.lifecycle.ViewModel
import com.example.solidmobile.domain.model.FruitSalad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class SaladsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        SaladsUiState(
            isLoading = true
        )
    )

    val uiState = _uiState.asStateFlow()

    fun loadSalads() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        try {
            val salads = listOf(
                FruitSalad(
                    id = 1,
                    name = "Осенний салат",
                    calories = 188.0,
                    dateUpdate = LocalDateTime.now()
                ),
                FruitSalad(
                    id = 2,
                    name = "Тропический салат",
                    calories = 245.5,
                    dateUpdate = LocalDateTime.now()
                ),
                FruitSalad(
                    id = 3,
                    name = "Зелёный салат",
                    calories = 132.0,
                    dateUpdate = LocalDateTime.now()
                )
            )

            _uiState.value = SaladsUiState(
                isLoading = false,
                salads = salads
            )
        } catch (exception: Exception) {
            _uiState.value = SaladsUiState(
                isLoading = false,
                errorMessage = exception.message
                    ?: "Не удалось загрузить список салатов"
            )
        }
    }
}