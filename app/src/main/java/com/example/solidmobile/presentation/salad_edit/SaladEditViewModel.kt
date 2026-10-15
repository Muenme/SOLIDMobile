package com.example.solidmobile.presentation.salad_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.navigation.Screen
import com.example.solidmobile.domain.repository.FruitRepository
import com.example.solidmobile.domain.repository.SaladRepository
import com.example.solidmobile.domain.usecase.DeleteSaladUseCase
import com.example.solidmobile.domain.usecase.SaveSaladUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaladEditViewModel @Inject constructor(
    private val saladRepository: SaladRepository,
    private val fruitRepository: FruitRepository,
    private val saveSalad: SaveSaladUseCase,
    private val deleteSalad: DeleteSaladUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SaladEditUiState())
    val uiState: StateFlow<SaladEditUiState> = _uiState.asStateFlow()

    private val _events = Channel<SaladEditEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun loadSalad(saladId: Int) = viewModelScope.launch {
        if (saladId == Screen.SaladEdit.NEW_SALAD) {
            _uiState.value = SaladEditUiState(isLoading = false, isEditing = false)
            return@launch
        }

        _uiState.update { it.copy(isLoading = true, isEditing = true, saladId = saladId) }

        val salad = saladRepository.getSaladById(saladId)
        val fruitIds = saladRepository.getFruitIdsForSalad(saladId)
        val fruits = fruitIds.mapNotNull { fruitRepository.getFruitById(it) }

        _uiState.value = SaladEditUiState(
            isLoading = false,
            isEditing = true,
            saladId = saladId,
            name = salad?.name.orEmpty(),
            selectedFruits = fruits,
            totalCalories = calculateTotalCalories(fruits)
        )
    }

    fun changeName(newName: String) {
        _uiState.update { it.copy(name = newName, nameError = null) }
    }

    fun addFruit(fruitId: Int) = viewModelScope.launch {
        val current = _uiState.value
        if (current.selectedFruits.any { it.id == fruitId }) return@launch

        val fruit = fruitRepository.getFruitById(fruitId) ?: return@launch
        val updated = current.selectedFruits + fruit

        _uiState.update {
            it.copy(selectedFruits = updated, totalCalories = calculateTotalCalories(updated))
        }
    }

    fun removeFruit(fruitId: Int) {
        val current = _uiState.value
        val updated = current.selectedFruits.filterNot { it.id == fruitId }

        viewModelScope.launch {
            val newCalories = calculateTotalCalories(updated)

            _uiState.update {
                it.copy(
                    selectedFruits = updated,
                    totalCalories = newCalories
                )
            }
        }
    }

    fun saveSalad() = viewModelScope.launch {
        val current = _uiState.value

        if (current.name.isBlank()) {
            _uiState.update { it.copy(nameError = "Введите название салата") }
            return@launch
        }
        if (current.selectedFruits.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Добавьте хотя бы один фрукт") }
            return@launch
        }

        _uiState.update { it.copy(isSaving = true, errorMessage = null) }

        val savedId = saveSalad(                              // ← было saladRepository.saveSalad(...)
            saladId = current.saladId,
            name = current.name,
            fruitIds = current.selectedFruits.map { it.id }
        )

        _uiState.update { it.copy(isSaving = false) }
        _events.send(SaladEditEvent.Saved(savedId))
    }

    fun deleteSalad() = viewModelScope.launch {
        val saladId = _uiState.value.saladId ?: return@launch // если id nullable
        if (!_uiState.value.isEditing) return@launch // на новом салате кнопки не должно быть

        _uiState.update { it.copy(isSaving = true, errorMessage = null) }
        deleteSalad(saladId)
        _uiState.update { it.copy(isSaving = false) }
        _events.send(SaladEditEvent.Deleted)
    }

    private suspend fun calculateTotalCalories(fruits: List<Fruit>): Double =
        fruits.sumOf { fruit -> fruitRepository.getNutritionsById(fruit.nutritionsId)?.calories ?: 0.0 }
}