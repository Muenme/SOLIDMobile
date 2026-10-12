package com.example.solidmobile.presentation.fruit_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solidmobile.domain.repository.FruitRepository
import com.example.solidmobile.domain.usecase.GetSortedByNameFruitsUseCase
import com.example.solidmobile.domain.usecase.SortOrderFruitByName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FruitListViewModel @Inject constructor(
    private val getSortedFruit: GetSortedByNameFruitsUseCase,
    private val repository: FruitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FruitListUiState())
    val uiState: StateFlow<FruitListUiState> = _uiState.asStateFlow()

    init {
        loadFruits() // грузим сразу при создании ViewModel
    }

    fun onSortOrderChanged(order: SortOrderFruitByName) {
        _uiState.update { it.copy(sortOrder = order) }
        loadFruits() // пересортировать с новым параметром
    }

    private fun loadFruits() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        val fruits = getSortedFruit(_uiState.value.sortOrder)
        _uiState.update { it.copy(isLoading = false, fruits = fruits) }
    }
}