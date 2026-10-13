package com.example.solidmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.solidmobile.data.local.entity.FruitEntity
import com.example.solidmobile.data.local.entity.NutritionsEntity

@Dao
interface FruitDao {

    //Fruit Create

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFruit(fruit: FruitEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFruits(fruits: List<FruitEntity>)

    //Fruit Read

    @Query("SELECT * FROM fruits")
    suspend fun getAllFruits(): List<FruitEntity>

    @Query("SELECT * FROM fruits WHERE id = :fruitId")
    suspend fun getFruitById(fruitId: Int): FruitEntity?

    @Query("SELECT COUNT(*) FROM fruits")
    suspend fun getFruitsCount(): Int

    //Fruit Update

    @Update
    suspend fun updateFruit(fruit: FruitEntity)

    //Fruit Delete

    @Delete
    suspend fun deleteFruit(fruit: FruitEntity)

    @Query("DELETE FROM fruits WHERE id = :fruitId")
    suspend fun deleteFruitById(fruitId: Int)

    @Query("DELETE FROM fruits")
    suspend fun deleteAllFruits()

    //Nutritions Create

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNutritions(nutritions: NutritionsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNutritions(nutritions: List<NutritionsEntity>)

    //Nutritions Read

    @Query("SELECT * FROM nutritions")
    suspend fun getAllNutritions(): List<NutritionsEntity>

    @Query("SELECT * FROM nutritions WHERE id = :nutritionsId")
    suspend fun getNutritionsById(nutritionsId: Int): NutritionsEntity?

    //Nutritions Update

    @Update
    suspend fun updateNutritions(nutritions: NutritionsEntity)

    //Nutritions Delete

    @Delete
    suspend fun deleteNutritions(nutritions: NutritionsEntity)

    @Query("DELETE FROM nutritions WHERE id = :nutritionsId")
    suspend fun deleteNutritionsById(nutritionsId: Int)

    @Query("DELETE FROM nutritions")
    suspend fun deleteAllNutritions()
}