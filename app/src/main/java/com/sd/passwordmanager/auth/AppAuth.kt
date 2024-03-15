package com.sd.passwordmanager.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAuth @Inject constructor(){
    private val _authStateFlow: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())

    val authStateFlow: StateFlow<AuthState> = _authStateFlow.asStateFlow()

    @Synchronized
    fun setAuth(id: Int, secretKey: String) {
        _authStateFlow.value = AuthState(id, secretKey)
    }

    @Synchronized
    fun removeAuth() {
        _authStateFlow.value = AuthState()
    }
}

data class AuthState(
    val id: Int = 0,
    val secretKeyItems: String = "",
)