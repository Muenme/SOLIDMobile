package com.example.solidmobile.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.usecase.SortOrderFruitByName
import com.example.solidmobile.presentation.fruit_list.FruitListUiState
import com.example.solidmobile.presentation.fruit_list.FruitListViewModel

// "Умная" версия — знает про ViewModel
@Composable
fun FruitListScreen(viewModel: FruitListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FruitListContent(
        uiState = uiState,
        onSortChange = viewModel::onSortOrderChanged
    )
}

// "Глупая" версия — state hoisting. Не знает про ViewModel вообще,
// поэтому её можно тестировать и превьюшить без реального ViewModel.
@Composable
fun FruitListContent(
    uiState: FruitListUiState,
    onSortChange: (SortOrderFruitByName) -> Unit
) {
    Column {
        Row {
            SortOrderFruitByName.entries.forEach { order ->
                TextButton(onClick = { onSortChange(order) }) {
                    Text(order.name)
                }
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.testTag("loading"))
        } else {
            LazyColumn {
                items(uiState.fruits) { fruit ->
                    FruitRow(
                        fruit = fruit
                    )
                }
            }
        }
    }
}

@Composable
fun FruitRow(fruit: Fruit) {
    Row(modifier = Modifier.testTag("fruit_row_${fruit.id}")) {
        Text(fruit.name, modifier = Modifier.weight(1f))
        Text("${fruit.genus}")
    }
}