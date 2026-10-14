package com.example.solidmobile.data.remote.datasource

import com.example.solidmobile.data.remote.model.FruitWithNutritions
import com.example.solidmobile.domain.model.Fruit

interface FruitRemoteDataSource {
    suspend fun getAllFruits(): List<FruitWithNutritions>
}