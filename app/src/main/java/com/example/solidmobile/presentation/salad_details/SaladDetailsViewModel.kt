package com.example.solidmobile.presentation.salad_details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.repository.FruitRepository
import com.example.solidmobile.domain.repository.SaladRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SaladDetailsViewModel @Inject constructor(
    private val saladRepository: SaladRepository,
    private val fruitRepository: FruitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SaladDetailsUiState())
    val uiState: StateFlow<SaladDetailsUiState> = _uiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadSalad(saladId: Int) = viewModelScope.launch {
        //включаем загрузку, сбрасываем прошлую ошибку
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        //забираем салат и список id его фруктов из SaladRepository
        val salad: FruitSalad? = saladRepository.getSaladById(saladId)
        if (salad == null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Салат не найден"
                )
            }
            return@launch
        }

        val fruitIds: List<Int> = saladRepository.getFruitIdsForSalad(saladId)

        //подтягиваем сами фрукты и их КБЖУ из FruitRepository
        val fruits: List<Fruit> = fruitIds.mapNotNull { fruitRepository.getFruitById(it) }
        val nutritionsByFruitId: Map<Int, Nutritions> = fruits
            .map { it.nutritionsId }
            .distinct()
            .mapNotNull { fruitRepository.getNutritionsById(it) }
            .associateBy { it.id }

        //отдаём UI готовый state
        _uiState.update {
            it.copy(
                isLoading = false,
                salad = salad,
                fruits = fruits,
                nutritionsByFruitId = nutritionsByFruitId
            )
        }
    }
}