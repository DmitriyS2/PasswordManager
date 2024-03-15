package com.sd.passwordmanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sd.passwordmanager.entity.ItemEntity

@Dao
interface ItemDao {
    //добавление нового ItemPassword или update старого
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: ItemEntity)

    //удалить ItemPassword
    @Query("DELETE FROM ItemEntity WHERE id = :id")
    suspend fun deleteItemPasswordById(id: Int)

    //дать все ItemPassword'ы по id мастер-пароля
    @Query("SELECT * FROM ItemEntity WHERE idMaster = :idMaster")
    suspend fun getAllItemPasswords(idMaster: Int): List<ItemEntity>?
}