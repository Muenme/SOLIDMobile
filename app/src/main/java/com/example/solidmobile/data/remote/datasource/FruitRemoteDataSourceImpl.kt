package com.example.solidmobile.data.remote.datasource

import com.example.solidmobile.data.remote.api.FruityviceApiService
import com.example.solidmobile.data.remote.mapper.toFruitWithNutritions
import com.example.solidmobile.data.remote.model.FruitWithNutritions
import javax.inject.Inject

class FruitRemoteDataSourceImpl @Inject constructor(
    private val api: FruityviceApiService
) : FruitRemoteDataSource {

    override suspend fun getAllFruits(): List<FruitWithNutritions> =
        api.getAllFruits().map { it.toFruitWithNutritions() }
}