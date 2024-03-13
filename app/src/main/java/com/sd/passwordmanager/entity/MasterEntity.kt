package com.sd.passwordmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MasterEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val password:String
)