package com.example.solidmobile.presentation.fruit_details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions

@Composable
fun FruitDetailsScreen(
    fruitId: Int,
    onBackClick: () -> Unit
) {
    val fruit = Fruit(
        id = fruitId,
        genus = "Malus",
        name = "Apple",
        family = "Rosaceae",
        order = "Rosales",
        nutritionsId = 1
    )

    val nutritions = Nutritions(
        id = 1,
        carbohydrates = 13.8,
        protein = 0.3,
        fat = 0.2,
        calories = 52.0,
        sugar = 10.4
    )

    Column {
        Text(text = fruit.name)
        Text(text = "Род: ${fruit.genus}")
        Text(text = "Семейство: ${fruit.family}")
        Text(text = "Порядок: ${fruit.order}")

        Text(text = "Пищевая ценность на 100 г")
        Text(text = "Калории: ${nutritions.calories} ккал")
        Text(text = "Белки: ${nutritions.protein} г")
        Text(text = "Жиры: ${nutritions.fat} г")
        Text(text = "Углеводы: ${nutritions.carbohydrates} г")
        Text(text = "Сахар: ${nutritions.sugar} г")

        Button (onClick = onBackClick) {
            Text("Назад")
        }
    }
}