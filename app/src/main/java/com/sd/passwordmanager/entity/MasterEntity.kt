package com.sd.passwordmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sd.passwordmanager.dto.MasterPassword

@Entity
class MasterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val password: String,
    val secretKeyItem: String
) {
    fun toDto() = MasterPassword(id, password, secretKeyItem)

    companion object {
        fun fromDto(dto: MasterPassword) =
            MasterEntity(dto.id, dto.password, dto.secretKeyItem)
    }
}