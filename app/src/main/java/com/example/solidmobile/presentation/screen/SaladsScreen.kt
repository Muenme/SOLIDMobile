package com.example.solidmobile.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SaladsScreen(
    onCreateSaladClick: () -> Unit,
    onSaladClick: (Int) -> Unit
) {
    Column() {
        Text(text = "Мои фруктовые салаты")

        Button(
            onClick = onCreateSaladClick
        ) {
            Text("Создать салат")
        }
    }
}