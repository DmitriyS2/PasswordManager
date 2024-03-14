package com.sd.passwordmanager.auth

import android.content.Context
import com.sd.passwordmanager.dto.MasterPassword
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAuth @Inject constructor(
//    @ApplicationContext private val context: Context,
) {
//    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
//    private val idKey = "id"
//    private val passwordKey = "password"

    private val _authStateFlow: MutableStateFlow<MasterPassword> = MutableStateFlow(MasterPassword())

    init {
//        val id = prefs.getLong(idKey, 0)
//        val password = prefs.getString(passwordKey, null)
//
//        if (id == 0L || password == null) {
        //    _authStateFlow = MutableStateFlow(AuthState())
//            with(prefs.edit()) {
//                clear()
//                apply()
//            }
//        } else {
//            _authStateFlow = MutableStateFlow(AuthState(id, password))
//        }
    }

    val authStateFlow: StateFlow<MasterPassword> = _authStateFlow.asStateFlow()

    @Synchronized
    fun setAuth(id: Int, password: String) {
        _authStateFlow.value = MasterPassword(id, password)
//        with(prefs.edit()) {
//            putLong(idKey, id)
//            putString(passwordKey, password)
//            apply()
//        }
    }

    @Synchronized
    fun removeAuth() {
        _authStateFlow.value = MasterPassword()
//        with(prefs.edit()) {
//            clear()
//            commit()
//        }
    }
}

//data class AuthState(
//    val id: Long? = 0,
//    val password: String? = null,
//)