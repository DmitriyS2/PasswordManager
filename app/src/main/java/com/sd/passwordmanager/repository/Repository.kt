package com.sd.passwordmanager.repository

import com.sd.passwordmanager.dto.ItemPassword
import com.sd.passwordmanager.dto.MasterPassword

interface Repository {
    suspend fun checkSignIn(password: String): MasterPassword?
    suspend fun signUp(password: String): MasterPassword?

    suspend fun getAllItemPasswords(idMaster: Int): List<ItemPassword>
    suspend fun addItem(itemPassword: ItemPassword): List<ItemPassword>
    suspend fun deleteItem(itemPassword: ItemPassword): List<ItemPassword>

}