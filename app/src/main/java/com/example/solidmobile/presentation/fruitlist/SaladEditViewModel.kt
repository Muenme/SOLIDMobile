package com.example.solidmobile.presentation.fruitlist


import androidx.lifecycle.ViewModel
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SaladEditViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        SaladEditUiState()
    )

    val uiState = _uiState.asStateFlow()

    private val testFruits = listOf(
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

    private val testNutritions = listOf(
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

    fun loadSalad(saladId: Int) {
        if (saladId == Screen.SaladEdit.NEW_SALAD) {
            _uiState.value = SaladEditUiState(
                isEditing = false,
                saladId = null
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            isEditing = true,
            saladId = saladId
        )

        // Позже здесь будет загрузка из репозитория:
        // viewModelScope.launch {
        //     val salad = saladRepository.getSaladById(saladId)
        //     val fruits = saladRepository.getFruitsForSalad(saladId)
        //     ...
        // }

        val loadedFruits = testFruits.take(2)

        _uiState.value = SaladEditUiState(
            isLoading = false,
            isEditing = true,
            saladId = saladId,
            name = "Фруктовый салат",
            selectedFruits = loadedFruits,
            totalCalories = calculateTotalCalories(loadedFruits)
        )
    }

    fun changeName(newName: String) {
        _uiState.value = _uiState.value.copy(
            name = newName,
            nameError = null
        )
    }

    fun addFruit(fruitId: Int) {
        val currentState = _uiState.value

        val fruit = testFruits.firstOrNull {
            it.id == fruitId
        } ?: return

        if (fruit in currentState.selectedFruits) {
            return
        }

        val updatedFruits = currentState.selectedFruits + fruit

        _uiState.value = currentState.copy(
            selectedFruits = updatedFruits,
            totalCalories = calculateTotalCalories(updatedFruits)
        )
    }

    fun removeFruit(fruitId: Int) {
        val currentState = _uiState.value

        val updatedFruits = currentState.selectedFruits.filter {
            it.id != fruitId
        }

        _uiState.value = currentState.copy(
            selectedFruits = updatedFruits,
            totalCalories = calculateTotalCalories(updatedFruits)
        )
    }

    fun saveSalad() {
        val currentState = _uiState.value

        if (currentState.name.isBlank()) {
            _uiState.value = currentState.copy(
                nameError = "Введите название салата"
            )
            return
        }

        if (currentState.selectedFruits.isEmpty()) {
            _uiState.value = currentState.copy(
                errorMessage = "Добавьте хотя бы один фрукт"
            )
            return
        }

        _uiState.value = currentState.copy(
            isSaving = true,
            errorMessage = null
        )

        // Позже здесь будет сохранение через репозиторий:
        // viewModelScope.launch {
        //     val savedId = saladRepository.saveSalad(...)
        //     ...
        // }

        val savedId = currentState.saladId ?: 42

        _uiState.value = currentState.copy(
            isSaving = false,
            isSaved = true,
            savedSaladId = savedId
        )
    }

    fun resetSavedState() {
        _uiState.value = _uiState.value.copy(
            isSaved = false,
            savedSaladId = null
        )
    }

    private fun calculateTotalCalories(
        fruits: List<Fruit>
    ): Double {
        return fruits.sumOf { fruit ->
            testNutritions.firstOrNull { nutrition ->
                nutrition.id == fruit.nutritionsId
            }?.calories ?: 0.0
        }
    }
}