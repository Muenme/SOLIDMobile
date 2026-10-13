package com.example.solidmobile.presentation.fruit_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solidmobile.domain.repository.FruitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FruitDetailsViewModel @Inject constructor(
    private val fruitRepository: FruitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FruitDetailsUiState()
    )

    val uiState: StateFlow<FruitDetailsUiState> =
        _uiState.asStateFlow()

    fun loadFruit(fruitId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val fruit = fruitRepository.getFruitById(fruitId)

                if (fruit == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Фрукт не найден"
                        )
                    }
                    return@launch
                }

                val nutritions = fruitRepository.getNutritionsById(
                    fruit.nutritionsId
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        fruit = fruit,
                        nutritions = nutritions
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message
                            ?: "Не удалось загрузить данные фрукта"
                    )
                }
            }
        }
    }
}