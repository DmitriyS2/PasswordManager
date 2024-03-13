package com.sd.passwordmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.sd.passwordmanager.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

}