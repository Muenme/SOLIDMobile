package com.example.solidmobile.presentation.salads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solidmobile.domain.repository.SaladRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaladsViewModel @Inject constructor(
    private val saladRepository: SaladRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SaladsUiState>(SaladsUiState.Loading)
    val uiState: StateFlow<SaladsUiState> = _uiState.asStateFlow()

    init {
        loadSalads()
    }

    fun loadSalads() {
        viewModelScope.launch {
            _uiState.value = SaladsUiState.Loading
            _uiState.value = runCatching { saladRepository.getAllSalads() }
                .fold(
                    onSuccess = { SaladsUiState.Success(it) },
                    onFailure = { SaladsUiState.Error(it.message ?: "Не удалось загрузить список салатов") }
                )
        }
    }
}