package com.sd.passwordmanager.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sd.passwordmanager.R
import com.sd.passwordmanager.databinding.FragmentCurrentBinding
import com.sd.passwordmanager.dto.ItemPassword
import com.sd.passwordmanager.util.ProtectData
import com.sd.passwordmanager.util.StringArg
import com.sd.passwordmanager.viewmodel.AuthViewModel
import com.sd.passwordmanager.viewmodel.MainViewModel

class CurrentFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private var fragmentBinding: FragmentCurrentBinding? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCurrentBinding =
            FragmentCurrentBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        val currentId = arguments?.textArg?.toInt() ?: 0

        val currentItem = viewModel.dataItem.value
            ?.firstOrNull { it.id == currentId } ?: ItemPassword()

        binding.apply {
            titleCurrent.setText(currentItem.title)
            siteCurrent.setText(currentItem.url)
            descriptionCurrent.setText(currentItem.description)
            passwordCurrent.setText(
                ProtectData.decrypt(
                    currentItem.password,
                    currentItem.secretKey
                )
            )
            passwordConfirmCurrent.setText(
                ProtectData.decrypt(
                    currentItem.password,
                    currentItem.secretKey
                )
            )

            buttonCancelCurrent.setOnClickListener {
                it.animTouch()
                findNavController()
                    .navigate(R.id.action_currentFragment_to_mainFragment)
            }

            buttonOkCurrent.setOnClickListener {
                it.animTouch()
                if (isFieldCorrect()) {
                    val itemPassword = ItemPassword(
                        id = currentId,
                        title = titleCurrent.text.toString(),
                        url = siteCurrent.text.toString(),
                        description = descriptionCurrent.text.toString(),
                        password = ProtectData.encrypt(
                            passwordCurrent.text.toString(),
                            authViewModel.data.value.secretKeyItems
                        ) ?: "",
                        idMaster = authViewModel.data.value.id,
                        secretKey = authViewModel.data.value.secretKeyItems
                    )
                    if (itemPassword != currentItem) {
                        viewModel.addItem(itemPassword)
                    }
                    findNavController()
                        .navigate(R.id.action_currentFragment_to_mainFragment)
                }
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private fun isFieldCorrect(): Boolean {
        var flag = true

        fragmentBinding?.apply {
            if (titleCurrent.text.isNullOrEmpty()) {
                titleCurrent.error = getString(R.string.field_mustnt_empty)
                flag = false
            }
            if (siteCurrent.text.isNullOrEmpty()) {
                siteCurrent.error = getString(R.string.field_mustnt_empty)
                flag = false
            }
            if (passwordCurrent.text.isNullOrEmpty()) {
                passwordCurrent.error = getString(R.string.field_mustnt_empty)
                flag = false
            }
            if (passwordConfirmCurrent.text.isNullOrEmpty()) {
                passwordConfirmCurrent.error = getString(R.string.field_mustnt_empty)
                flag = false
            }

            if (passwordCurrent.text.toString() != passwordConfirmCurrent.text.toString()) {
                passwordCurrent.error = getString(R.string.password_doesnt_confirm)
                passwordConfirmCurrent.error = getString(R.string.password_doesnt_confirm)
                flag = false
            }
        }
        return flag
    }
}