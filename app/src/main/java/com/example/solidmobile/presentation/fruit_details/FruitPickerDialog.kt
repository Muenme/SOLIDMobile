package com.example.solidmobile.presentation.fruit_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.solidmobile.domain.model.Fruit

@Composable
fun FruitPickerDialog(
    onDismiss: () -> Unit,
    onFruitClick: (Int) -> Unit
) {
    val fruits = listOf(
        Fruit(
            id = 1,
            genus = "Malus",
            name = "Apple",
            family = "Rosaceae",
            order = "Rosales",
            nutritionsId = 1
        ),
        Fruit(
            id = 2,
            genus = "Musa",
            name = "Banana",
            family = "Musaceae",
            order = "Zingiberales",
            nutritionsId = 2
        )
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Выберите фрукт")
        },
        text = {
            LazyColumn {
                items(
                    items = fruits,
                    key = { fruit -> fruit.id }
                ) { fruit ->
                    FruitItem(
                        fruit = fruit,
                        onClick = {
                            onFruitClick(fruit.id)
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}

@Composable
fun FruitItem(
    fruit: Fruit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(
            onClick = onClick
        )
    ) {
        Text(text = fruit.name)
        Text(text = "Семейство: ${fruit.family}")
        Text(text = "Род: ${fruit.genus}")
    }
}