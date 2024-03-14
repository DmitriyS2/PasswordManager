package com.sd.passwordmanager.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sd.passwordmanager.R
import com.sd.passwordmanager.auth.AppAuth
import com.sd.passwordmanager.viewmodel.AuthViewModel
import com.sd.passwordmanager.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInOutDialogFragment(
    val title: String,
    val textPosButton: String,
    val textNegButton: String,
    val flagSignOut: Boolean,
) : DialogFragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var auth: AppAuth

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        return builder
            .setIcon(R.drawable.warning_24)
            .setTitle(title)
            .setCancelable(true)
            .setPositiveButton(textPosButton) { _, _ ->
                if (flagSignOut) {
                    auth.removeAuth() //SignOut "yes"
                    authViewModel.changeShowUIfromAuth(4) //не в системе
                } else {
                    authViewModel.changeShowUIfromAuth(1) //войти
                }
                dialog?.cancel()
            }
            .setNegativeButton(textNegButton) { _, _ ->
                dialog?.cancel()
                if (!flagSignOut) {
                    authViewModel.changeShowUIfromAuth(2) //регистрация
                }
            }.create()
    }
}