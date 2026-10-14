package com.example.solidmobile.data.remote.api

import com.example.solidmobile.data.remote.dto.FruitDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FruityviceApiService {
    @GET("api/fruit/all")
    suspend fun getAllFruits(): List<FruitDto>
}