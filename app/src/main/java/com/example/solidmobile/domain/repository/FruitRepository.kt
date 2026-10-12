package com.example.solidmobile.domain.repository

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions

interface FruitRepository{
    suspend fun getAllFruits(): List<Fruit>
    suspend fun getFruitById(id: Int): Fruit?
    suspend fun getAllNutritions(): List<Nutritions>
    suspend fun getNutritionsById(id: Int): Nutritions?

}