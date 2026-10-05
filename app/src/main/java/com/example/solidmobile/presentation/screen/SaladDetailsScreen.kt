package com.example.solidmobile.ui.screen.salad_details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.presentation.fruitlist.SaladDetailsUiState
import com.example.solidmobile.presentation.fruitlist.SaladDetailsViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaladDetailsScreen(
    saladId: Int,
    onBackClick: () -> Unit,
    onFruitClick: (Int) -> Unit = {},
    viewModel: SaladDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(saladId) {
        viewModel.loadSalad(saladId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Состав салата")
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
                )
            )
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
                    message = uiState.errorMessage ?: "Не удалось загрузить салат",
                    modifier = Modifier.padding(innerPadding)
                )
            }

            uiState.salad != null -> {
                SaladDetailsContent(
                    uiState = uiState,
                    onFruitClick = onFruitClick,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            else -> {
                ErrorContent(
                    message = "Салат не найден",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun SaladDetailsContent(
    uiState: SaladDetailsUiState,
    onFruitClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val salad = uiState.salad ?: return

    val dateFormatter = DateTimeFormatter.ofPattern(
        "dd.MM.yyyy HH:mm",
        Locale("ru")
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
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
                        text = salad.name,
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

                    DetailInfoRow(
                        title = "Общая калорийность",
                        value = "${formatNumber(salad.calories)} ккал"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DetailInfoRow(
                        title = "Дата обновления",
                        value = salad.dateUpdate.format(dateFormatter)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DetailInfoRow(
                        title = "Количество фруктов",
                        value = uiState.fruits.size.toString()
                    )
                }
            }
        }

        item {
            Text(
                text = "Фрукты в салате",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 4.dp
                )
            )
        }

        if (uiState.fruits.isEmpty()) {
            item {
                EmptyFruitsContent()
            }
        } else {
            items(
                items = uiState.fruits,
                key = { fruit -> fruit.id }
            ) { fruit ->

                val nutrition = uiState.nutritionsByFruitId[fruit.id]

                FruitInSaladItem(
                    fruit = fruit,
                    calories = nutrition?.calories,
                    onClick = {
                        onFruitClick(fruit.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun DetailInfoRow(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
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
private fun FruitInSaladItem(
    fruit: Fruit,
    calories: Double?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FruitIcon(
                fruitName = fruit.name
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fruit.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${fruit.family} • ${fruit.genus}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.75f
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = calories?.let {
                        "${formatNumber(it)} ккал"
                    } ?: "Нет данных",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Состав",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun FruitIcon(
    fruitName: String,
    modifier: Modifier = Modifier
) {
    val firstLetter = fruitName
        .trim()
        .firstOrNull()
        ?.uppercase()
        ?: "?"

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = firstLetter,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
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

@Composable
private fun EmptyFruitsContent(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = "В этом салате пока нет фруктов.",
            modifier = Modifier.padding(16.dp),
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