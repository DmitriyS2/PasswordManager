package com.sd.passwordmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sd.passwordmanager.dto.ItemPassword

@Entity
class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val url: String,
    val description: String,
    val password: String,
    val idMaster: Int = 0,
    val secretKey: String = "",
) {
    fun toDto() = ItemPassword(id, title, url, description, password, idMaster, secretKey)

    companion object {
        fun fromDto(dto: ItemPassword) =
            ItemEntity(
                dto.id,
                dto.title,
                dto.url,
                dto.description,
                dto.password,
                dto.idMaster,
                dto.secretKey
            )
    }
}

fun List<ItemEntity>.toDto(): List<ItemPassword> = map(ItemEntity::toDto)