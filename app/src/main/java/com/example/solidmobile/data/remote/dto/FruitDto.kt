package com.example.solidmobile.data.remote.dto

data class FruitDto(
    val id: Int,
    val name: String,
    val family: String,
    val genus: String,
    val order: String,
    val nutritions: NutritionsDto
)