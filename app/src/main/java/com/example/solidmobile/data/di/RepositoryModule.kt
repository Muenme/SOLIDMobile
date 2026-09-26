package com.example.solidmobile.data.di

import com.example.solidmobile.data.repository.FruitRepositoryImpl
import com.example.solidmobile.domain.repository.FruitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // один репозиторий на всё приложение
abstract class RepositoryModule {

    @Binds
    abstract fun bindFruitRepository(
        impl: FruitRepositoryImpl
    ): FruitRepository
    // ↑ "Когда кто-то попросит FruitRepository — подставляй FruitRepositoryImpl"
}