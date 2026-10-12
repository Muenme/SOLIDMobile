package com.example.solidmobile.presentation.salad_details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.model.Nutritions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class SaladDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        SaladDetailsUiState()
    )

    val uiState = _uiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadSalad(saladId: Int) {
        _uiState.value = SaladDetailsUiState(
            isLoading = true
        )

        try {
            val salad = FruitSalad(
                id = saladId,
                name = "Фруктовый салат",
                calories = 188.0,
                dateUpdate = LocalDateTime.now()
            )

            val fruits = listOf(
                Fruit(
                    id = 1,
                    genus = "Malus",
                    name = "Яблоко",
                    family = "Rosaceae",
                    order = "Rosales",
                    nutritionsId = 1
                ),
                Fruit(
                    id = 2,
                    genus = "Musa",
                    name = "Банан",
                    family = "Musaceae",
                    order = "Zingiberales",
                    nutritionsId = 2
                ),
                Fruit(
                    id = 3,
                    genus = "Citrus",
                    name = "Апельсин",
                    family = "Rutaceae",
                    order = "Sapindales",
                    nutritionsId = 3
                )
            )

            val nutritions = listOf(
                Nutritions(
                    id = 1,
                    carbohydrates = 13.8,
                    protein = 0.3,
                    fat = 0.2,
                    calories = 52.0,
                    sugar = 10.4
                ),
                Nutritions(
                    id = 2,
                    carbohydrates = 22.8,
                    protein = 1.1,
                    fat = 0.3,
                    calories = 89.0,
                    sugar = 12.2
                ),
                Nutritions(
                    id = 3,
                    carbohydrates = 11.8,
                    protein = 0.9,
                    fat = 0.1,
                    calories = 47.0,
                    sugar = 9.4
                )
            )

            val nutritionsByFruitId = fruits.associate { fruit ->
                val nutrition = nutritions.firstOrNull {
                    it.id == fruit.nutritionsId
                }

                fruit.id to nutrition
            }.filterValues { nutrition ->
                nutrition != null
            }.mapValues { entry ->
                entry.value!!
            }

            _uiState.value = SaladDetailsUiState(
                isLoading = false,
                salad = salad,
                fruits = fruits,
                nutritionsByFruitId = nutritionsByFruitId
            )
        } catch (exception: Exception) {
            _uiState.value = SaladDetailsUiState(
                isLoading = false,
                errorMessage = exception.message
                    ?: "Не удалось загрузить данные салата"
            )
        }
    }
}