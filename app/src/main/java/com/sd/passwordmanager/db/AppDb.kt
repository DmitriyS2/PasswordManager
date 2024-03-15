package com.sd.passwordmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sd.passwordmanager.dao.ItemDao
import com.sd.passwordmanager.dao.MasterDao
import com.sd.passwordmanager.entity.ItemEntity
import com.sd.passwordmanager.entity.MasterEntity

@Database(
    entities = [ItemEntity::class, MasterEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun masterDao(): MasterDao
}