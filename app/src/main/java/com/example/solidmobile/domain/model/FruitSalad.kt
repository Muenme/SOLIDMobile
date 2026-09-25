package com.example.solidmobile.domain.model

import java.time.LocalDateTime

data class FruitSalad(
    val id: Int,
    val name: String,
    val calories: Double,
    val dateUpdate: LocalDateTime
)