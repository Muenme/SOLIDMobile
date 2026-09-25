package com.example.solidmobile.data.repository

import android.os.Build
import android.os.VibrationEffect
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composition
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.model.СompositionFruitSalad
import com.example.solidmobile.domain.repository.FruitRepository
import kotlinx.coroutines.delay
import java.time.LocalDateTime

class FruitRepositoryImpl : FruitRepository{
    private val nutritions = mutableListOf(
        Nutritions(
            id = 1,
            carbohydrates = 5.5,
            protein = 0.8,
            fat = 0.4,
            calories = 29.0,
            sugar = 5.4
        ),
        Nutritions(
            id = 2,
            carbohydrates = 22.0,
            protein = 1.0,
            fat = 0.2,
            calories = 96.0,
            sugar = 17.2
        ),
        Nutritions(
            id = 3,
            carbohydrates = 15.0,
            protein = 0.4,
            fat = 0.1,
            calories = 57.0,
            sugar = 10.0
        )
    )
    private val fruit = mutableListOf(
        Fruit(
            id = 1,
            genus = "TestFragaria",
            name = "TestStrawberry",
            family = "TestRosaceae",
            order = "TestRosales",
            nutritionsId = 1),
        Fruit(
            id = 2,
            genus = "TestMusa",
            name = "TestBanana",
            family = "TestMusaceae",
            order = "TestZingiberales",
            nutritionsId = 2),
        Fruit(
            id = 3,
            genus = "TestPyrus",
            name = "TestPear",
            family = "TestRosaceae",
            order = "TestRosales",
            nutritionsId = 3)
    )
    @RequiresApi(Build.VERSION_CODES.O)
    private val salad = mutableListOf(
        FruitSalad(
            id = 1,
            name = "TestSalad",
            calories = 42.42,
            dateUpdate = LocalDateTime.now()
        )
    )
    private val сompositionFruitSalad = mutableListOf(
        СompositionFruitSalad(
            id = 1,
            fruitId = 1,
            saladId = 1
        )
    )
    override suspend fun getNutritions(): List<Nutritions> {
        TODO("Not yet implemented")
    }

    override suspend fun getFruit(): List<Fruit> {
        delay(300)
        return fruit.toList()
    }

    override suspend fun getSalad(): List<FruitSalad> {
        TODO("Not yet implemented")
    }

    override suspend fun getComposition(): List<Composition> {
        TODO("Not yet implemented")
    }

    override suspend fun addToSalad(fruitId: Int, saladId: Int) {
        сompositionFruitSalad.add(
            СompositionFruitSalad(
                id = 1,
                fruitId = fruitId,
                saladId = saladId
        ))
    }
}