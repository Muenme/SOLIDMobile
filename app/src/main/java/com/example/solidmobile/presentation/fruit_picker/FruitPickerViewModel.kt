package com.example.solidmobile.presentation.fruit_picker

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
class FruitPickerViewModel @Inject constructor(
    private val fruitRepository: FruitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FruitPickerUiState()
    )

    val uiState: StateFlow<FruitPickerUiState> =
        _uiState.asStateFlow()

    init {
        loadFruits()
    }

    fun loadFruits() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val fruits = fruitRepository.getAllFruits()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        fruits = fruits
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message
                            ?: "Не удалось загрузить список фруктов"
                    )
                }
            }
        }
    }
}