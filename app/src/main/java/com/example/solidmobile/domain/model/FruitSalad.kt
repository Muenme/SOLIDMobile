package com.example.solidmobile.domain.model

import java.util.Date

data class FruitSalad(
    val id: Int,
    val name: String,
    val calories: Double,
    val dateUpdate: Date
)