package com.example.solidmobile.domain.repository

import androidx.compose.runtime.Composition
import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.FruitSalad
import com.example.solidmobile.domain.model.Nutritions

interface FruitRepository{
    suspend fun getNutritions(): List<Nutritions>
    suspend fun getFruit(): List<Fruit>
    suspend fun getSalad(): List<FruitSalad>
    suspend fun getComposition(): List<Composition>
    suspend fun addToSalad(fruitId: Int, saladId: Int)
}