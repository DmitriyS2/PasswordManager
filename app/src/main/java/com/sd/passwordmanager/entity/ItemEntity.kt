package com.sd.passwordmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title:String,
    val url:String,
    val description:String,
    val password:String,
    val master:String
) {
    fun toDto() = ItemEntity(id, title, url, description, password, master)

    companion object {
        fun fromDto(dto: ItemEntity) =
            ItemEntity(dto.id, dto.title, dto.url, dto.description, dto.password, dto.master)
    }
}