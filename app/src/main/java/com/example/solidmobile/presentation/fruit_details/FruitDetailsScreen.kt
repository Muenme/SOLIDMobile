package com.example.solidmobile.presentation.fruit_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.presentation.common.ErrorContent
import com.example.solidmobile.presentation.common.LoadingContent
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitDetailsScreen(
    fruitId: Int,
    onBackClick: () -> Unit,
    viewModel: FruitDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(fruitId) {
        viewModel.loadFruit(fruitId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = uiState.fruit?.name ?: "Фрукт")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ))
        }
    ) { innerPadding ->

        when {
            uiState.isLoading -> {
                LoadingContent(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            uiState.errorMessage != null -> {
                ErrorContent(
                    message = uiState.errorMessage ?: "Ошибка",
                    modifier = Modifier.padding(innerPadding)
                )
            }

            uiState.fruit != null -> {
                CompositionFruit(
                    fruit = uiState.fruit!!,
                    nutritions = uiState.nutritions!!,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            else -> {
                ErrorContent(
                    message = "Фрукт не найден",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

}
@Composable
private fun CompositionFruit(
    fruit: Fruit,
    nutritions: Nutritions,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Card (
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
    )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = fruit.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(
                    alpha = 0.2f
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            DetailRow(
                label = "Род",
                value = fruit.genus
            )

            Spacer(modifier = Modifier.height(8.dp))

            DetailRow(
                label = "Семейство",
                value = fruit.family
            )

            Spacer(modifier = Modifier.height(8.dp))

            DetailRow(
                label = "Порядок",
                value = fruit.order
            )
        }
    }

    if (nutritions != null) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Пищевая ценность на 100 г",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                NutritionRow(
                    label = "Калории",
                    value = "${formatNumber(nutritions.calories)} ккал"
                )

                Spacer(modifier = Modifier.height(8.dp))

                NutritionRow(
                    label = "Белки",
                    value = "${formatNumber(nutritions.protein)} г"
                )

                Spacer(modifier = Modifier.height(8.dp))

                NutritionRow(
                    label = "Жиры",
                    value = "${formatNumber(nutritions.fat)} г"
                )

                Spacer(modifier = Modifier.height(8.dp))

                NutritionRow(
                    label = "Углеводы",
                    value = "${formatNumber(nutritions.carbohydrates)} г"
                )

                Spacer(modifier = Modifier.height(8.dp))

                NutritionRow(
                    label = "Сахар",
                    value = "${formatNumber(nutritions.sugar)} г"
                )
            }
        }
    } else {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = "Данные о пищевой ценности не найдены.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(
                alpha = 0.8f
            )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun NutritionRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun formatNumber(number: Double): String {
    return if (number % 1.0 == 0.0) {
        number.toInt().toString()
    } else {
        String.format(
            Locale.US,
            "%.1f",
            number
        )
    }
}