package com.sd.passwordmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.passwordmanager.dto.ItemPassword
import com.sd.passwordmanager.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    private val _dataItem:MutableLiveData<List<ItemPassword>> = MutableLiveData()
    val dataItem:LiveData<List<ItemPassword>>
        get() = _dataItem

    fun getAll(master:String) {
        viewModelScope.launch {
            try {
                _dataItem.value = repository.getAllItemPasswords(master)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addItem(itemPassword: ItemPassword) {
        viewModelScope.launch {
            try {
                _dataItem.value = repository.addItem(itemPassword)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteItem(itemPassword: ItemPassword) {
        viewModelScope.launch {
            try {
                _dataItem.value = repository.deleteItem(itemPassword)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun changeVisiblePassword(itemPassword: ItemPassword) {
        _dataItem.value = _dataItem.value?.map {
            it.copy(isOpenPassword = if(it.id==itemPassword.id) !it.isOpenPassword else it.isOpenPassword)
        }
    }

}