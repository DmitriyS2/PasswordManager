package com.sd.passwordmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.passwordmanager.auth.AppAuth
import com.sd.passwordmanager.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: AppAuth,
    private val repository: Repository
) : ViewModel() {
    val data = auth.authStateFlow

    val authenticated: Boolean
        get() = data.value.id != 0

    val authenticatedLiveData: MutableLiveData<Boolean> = MutableLiveData(authenticated)

    private val _showUIfromAuth: MutableLiveData<Int> = MutableLiveData(5)
    val showUIfromAuth: LiveData<Int>
        get() = _showUIfromAuth

    private val _stateAuth: MutableLiveData<Int> = MutableLiveData(0)
    val stateAuth:LiveData<Int>
        get() = _stateAuth

    init {
        _showUIfromAuth.value = if (authenticated) 0 else 3
    }

    fun changeShowUIfromAuth(number: Int) {
        _showUIfromAuth.value = number
    }

    fun changeStateAuth(number: Int) {
        _stateAuth.value = number
    }

    fun signIn(password: String) {
        viewModelScope.launch {
            try {
                val user = repository.checkSignIn(password)
                if (user==null) {
                    _stateAuth.value = -1
                } else {
                    if (user.password == password) {
                        auth.setAuth(user.id, user.password)
                        _stateAuth.value = 1
                    } else {
                        _stateAuth.value = -1
                    }
                }
            } catch (e: Exception) {
                _stateAuth.value = -1
            }
        }
    }

    fun signUp(password: String) {
            viewModelScope.launch {
                try {
                    val user = repository.checkSignIn(password)
                    if (user==null) {
                        val newUser = repository.signUp(password)
                        if(newUser!=null) {
                            auth.setAuth(newUser.id, newUser.password)
                            _stateAuth.value = 1
                        } else {
                            _stateAuth.value = -2
                        }
                    } else {
                        _stateAuth.value = -3
                    }
                } catch (e:Exception) {
                    e.printStackTrace()
                    _stateAuth.value = -2
                }
            }
        }
    }




