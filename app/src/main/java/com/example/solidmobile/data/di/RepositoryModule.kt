package com.example.solidmobile.data.di

import com.example.solidmobile.data.remote.datasource.FruitRemoteDataSource
import com.example.solidmobile.data.remote.datasource.FruitRemoteDataSourceImpl
import com.example.solidmobile.data.repository.FruitRepositoryImpl
import com.example.solidmobile.data.repository.SaladRepositoryImpl
import com.example.solidmobile.domain.repository.FruitRepository
import com.example.solidmobile.domain.repository.SaladRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // один репозиторий на всё приложение
abstract class RepositoryModule {

    @Binds
    abstract fun bindFruitRepository(
        impl: FruitRepositoryImpl
    ): FruitRepository
    // ↑ "Когда кто-то попросит FruitRepository — подставляй FruitRepositoryImpl"

    @Binds
    abstract fun bindSaladRepository(
        impl: SaladRepositoryImpl
    ): SaladRepository

    @Binds
    abstract fun bindFruitRemoteDataSource(
        impl: FruitRemoteDataSourceImpl
    ): FruitRemoteDataSource
}