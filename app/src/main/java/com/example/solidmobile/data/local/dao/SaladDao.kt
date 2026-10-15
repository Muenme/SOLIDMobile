package com.example.solidmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.solidmobile.data.local.entity.FruitSaladEntity
import com.example.solidmobile.data.local.entity.SaladFruitCrossRef

@Dao
interface SaladDao {

    //FruitSalad Create

    @Insert
    suspend fun insertSalad(salad: FruitSaladEntity): Long

    //FruitSalad Read

    @Query("SELECT * FROM fruit_salads ORDER BY dateUpdate DESC")
    suspend fun getAllSalads(): List<FruitSaladEntity>

    @Query("SELECT * FROM fruit_salads WHERE id = :saladId")
    suspend fun getSaladById(saladId: Int): FruitSaladEntity?

    @Query("SELECT COUNT(*) FROM fruit_salads")
    suspend fun getSaladsCount(): Int

    //FruitSalad Update

    @Update
    suspend fun updateSalad(salad: FruitSaladEntity)

    //FruitSalad Delete

    @Delete
    suspend fun deleteSalad(salad: FruitSaladEntity)

    @Query("DELETE FROM fruit_salads WHERE id = :saladId")
    suspend fun deleteSaladById(saladId: Int)

    @Query("DELETE FROM fruit_salads")
    suspend fun deleteAllSalads()

    //SaladFruitCrossRef Create

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFruitToSalad(crossRef: SaladFruitCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFruitsToSalad(crossRefs: List<SaladFruitCrossRef>)

    //SaladFruitCrossRef Read

    @Query("SELECT fruitId FROM salad_fruit_cross_ref WHERE saladId = :saladId")
    suspend fun getFruitIdsForSalad(saladId: Int): List<Int>

    @Query("SELECT saladId FROM salad_fruit_cross_ref WHERE fruitId = :fruitId")
    suspend fun getSaladIdsForFruit(fruitId: Int): List<Int>

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM salad_fruit_cross_ref 
            WHERE saladId = :saladId AND fruitId = :fruitId
        )
    """)
    suspend fun isFruitInSalad(saladId: Int, fruitId: Int): Boolean

    //SaladFruitCrossRef Delete
    // Update для cross-таблицы не нужен: у нее нет своих полей, кроме ключей
    // "обновление" связи — это всегда delete + insert, Update() здесь бессмысленнен

    @Query("DELETE FROM salad_fruit_cross_ref WHERE saladId = :saladId AND fruitId = :fruitId")
    suspend fun removeFruitFromSalad(saladId: Int, fruitId: Int)

    @Query("DELETE FROM salad_fruit_cross_ref WHERE saladId = :saladId")
    suspend fun removeAllFruitsFromSalad(saladId: Int)
}