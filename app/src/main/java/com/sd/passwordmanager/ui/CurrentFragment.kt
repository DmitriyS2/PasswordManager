package com.sd.passwordmanager.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sd.passwordmanager.R
import com.sd.passwordmanager.databinding.FragmentCurrentBinding
import com.sd.passwordmanager.dto.ItemPassword
import com.sd.passwordmanager.util.StringArg
import com.sd.passwordmanager.viewmodel.AuthViewModel
import com.sd.passwordmanager.viewmodel.MainViewModel

class CurrentFragment : Fragment() {

    private val viewModel:MainViewModel by activityViewModels()
    private val authViewModel:AuthViewModel by activityViewModels()
    private var fragmentBinding: FragmentCurrentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCurrentBinding =
            FragmentCurrentBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        val currentId = arguments?.textArg?.toInt() ?: 0

                val currentItem = viewModel.dataItem.value
            ?.firstOrNull() { it.id == currentId } ?: ItemPassword()

        binding.apply {
            titleCurrent.setText(currentItem.title)
            siteCurrent.setText(currentItem.url)
            descriptionCurrent.setText(currentItem.description)
            passwordCurrent.setText(currentItem.password)
            passwordConfirmCurrent.setText(currentItem.password)

            buttonCancelCurrent.setOnClickListener{
                it.animTouch()
                findNavController()
                    .navigate(R.id.action_currentFragment_to_mainFragment)
            }

            buttonOkCurrent.setOnClickListener{
                it.animTouch()
                if(isFieldCorrect()) {
                    val itemPassword = ItemPassword(
                        id = currentId,
                        title = titleCurrent.text.toString(),
                        url = siteCurrent.text.toString(),
                        description = descriptionCurrent.text.toString(),
                        password = passwordCurrent.text.toString(),
                        master = authViewModel.data.value.password
                    )
                    if(itemPassword!=currentItem) {
                        Log.d("MyLog", "currentItem новый")
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
                titleCurrent.error = "Поле не может быть пустым"
                flag = false
            }
            if (siteCurrent.text.isNullOrEmpty()) {
                siteCurrent.error = "Поле не может быть пустым"
                flag = false
            }
            if (passwordCurrent.text.isNullOrEmpty()) {
                passwordCurrent.error = "Поле не может быть пустым"
                flag = false
            }
            if (passwordConfirmCurrent.text.isNullOrEmpty()) {
                passwordConfirmCurrent.error = "Поле не может быть пустым"
                flag = false
            }

            if (passwordCurrent.text.toString() != passwordConfirmCurrent.text.toString()) {
                passwordCurrent.error = "Пароль не совпадает"
                passwordConfirmCurrent.error = "Пароль не совпадает"
                flag = false
            }
        }
        return flag
    }
}