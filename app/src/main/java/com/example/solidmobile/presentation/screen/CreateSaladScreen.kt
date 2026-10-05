package com.example.solidmobile.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CreateSaladScreen(
    onBackClick: () -> Unit,
    onChooseFruitsClick: () -> Unit,
    onSaveClick: (Int) -> Unit
) {
    Column {
        Text(text = "Создание фруктового салата")

        Button(
            onClick = onChooseFruitsClick
        ) {
            Text("Добавить фрукт")
        }

        Button(
            onClick = {
                val createdSaladId = 1
                onSaveClick(createdSaladId)
            }
        ) {
            Text("Сохранить")
        }

        Button(
            onClick = onBackClick
        ) {
            Text("Назад")
        }
    }
}