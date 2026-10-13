package com.example.solidmobile.data.di

import android.content.Context
import androidx.room.Room
import com.example.solidmobile.data.local.AppDatabase
import com.example.solidmobile.data.local.dao.FruitDao
import com.example.solidmobile.data.local.dao.SaladDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "solid_mobile.db"
        ).build()
    }

    @Provides
    fun provideFruitDao(database: AppDatabase): FruitDao = database.fruitDao()

    @Provides
    fun provideSaladDao(database: AppDatabase): SaladDao = database.saladDao()
}