package com.sd.passwordmanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sd.passwordmanager.entity.MasterEntity

@Dao
interface MasterDao {
    //добавление нового мастер-пароля
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(masterEntity: MasterEntity)

    //дать мастер-пароль
    @Query("SELECT * FROM MasterEntity")
    suspend fun getMasterPassword(): List<MasterEntity>
}