package com.sd.passwordmanager.repository

import com.sd.passwordmanager.dao.ItemDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val itemDao: ItemDao
):Repository {

}