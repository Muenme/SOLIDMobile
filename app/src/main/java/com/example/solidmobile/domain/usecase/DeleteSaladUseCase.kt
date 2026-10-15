package com.example.solidmobile.domain.usecase

import com.example.solidmobile.domain.repository.SaladRepository
import javax.inject.Inject

class DeleteSaladUseCase @Inject constructor(
    private val saladRepository: SaladRepository
) {
    suspend operator fun invoke(saladId: Int) {
        saladRepository.deleteSalad(saladId)
    }
}