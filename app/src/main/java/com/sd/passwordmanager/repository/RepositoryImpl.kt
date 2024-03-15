package com.sd.passwordmanager.repository

import com.sd.passwordmanager.dao.ItemDao
import com.sd.passwordmanager.dao.MasterDao
import com.sd.passwordmanager.dto.ItemPassword
import com.sd.passwordmanager.dto.MasterPassword
import com.sd.passwordmanager.entity.ItemEntity
import com.sd.passwordmanager.entity.MasterEntity
import com.sd.passwordmanager.entity.toDto
import com.sd.passwordmanager.util.ProtectData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val itemDao: ItemDao,
    private val masterDao: MasterDao
) : Repository {
    override suspend fun checkSignIn(password: String): MasterPassword? {
        return masterDao.getMasterPassword(password)?.toDto()
    }

    override suspend fun signUp(password: String): MasterPassword? {
        val secretHash = ProtectData.generateRandomSalt()
        val user = MasterPassword(password = password, secretKeyItem = secretHash)
        masterDao.insert(MasterEntity.fromDto(user))
        return checkSignIn(password)
    }

    override suspend fun getAllItemPasswords(idMaster: Int) =
        itemDao.getAllItemPasswords(idMaster)?.toDto() ?: emptyList()

    override suspend fun addItem(itemPassword: ItemPassword): List<ItemPassword> {
        itemDao.insert(ItemEntity.fromDto(itemPassword))
        return getAllItemPasswords(itemPassword.idMaster)
    }

    override suspend fun deleteItem(itemPassword: ItemPassword): List<ItemPassword> {
        itemDao.deleteItemPasswordById(itemPassword.id)
        return getAllItemPasswords(itemPassword.idMaster)
    }


}