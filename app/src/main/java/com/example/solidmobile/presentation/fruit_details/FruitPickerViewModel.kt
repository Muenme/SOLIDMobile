package com.example.solidmobile.presentation.fruit_details

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.repository.FruitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FruitPickerViewModel @Inject constructor(
    private val fruitRepository: FruitRepository
) : ViewModel() {
    private val _fruits = MutableStateFlow<List<Fruit>>(emptyList())
    val fruits: StateFlow<List<Fruit>> = _fruits.asStateFlow()

    init {
        viewModelScope.launch { _fruits.value = fruitRepository.getAllFruits() }
    }
}

@Composable
fun FruitPickerDialog(
    onDismiss: () -> Unit,
    onFruitSelected: (Int) -> Unit,
    viewModel: FruitPickerViewModel = hiltViewModel()
) {
    val fruits by viewModel.fruits.collectAsStateWithLifecycle()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выберите фрукт") },
        text = {
            LazyColumn {
                items(fruits, key = { it.id }) { fruit ->
                    FruitItem (fruit = fruit, onClick = {
                        onFruitSelected(fruit.id)
                        onDismiss()
                    })
                }
            }
        },
        confirmButton = { TextButton (onClick = onDismiss) { Text("Закрыть") } }
    )
}