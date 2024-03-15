package com.sd.passwordmanager.dto

data class ItemPassword(
    val id: Int = 0,
    val title: String = "",
    val url: String = "",
    val description: String = "",
    val password: String = "",
    val idMaster: Int = 0,
    val secretKey: String = "",
    val isOpenPassword: Boolean = false
)
