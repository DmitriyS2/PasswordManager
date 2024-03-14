package com.sd.passwordmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sd.passwordmanager.dto.ItemPassword

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
    fun toDto() = ItemPassword(id, title, url, description, password, master)

    companion object {
        fun fromDto(dto: ItemPassword) =
            ItemEntity(dto.id, dto.title, dto.url, dto.description, dto.password, dto.master)
    }
}
fun List<ItemEntity>.toDto(): List<ItemPassword> = map(ItemEntity::toDto)
fun List<ItemPassword>.toEntity(): List<ItemEntity> = map(ItemEntity::fromDto)