package com.sd.passwordmanager.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sd.passwordmanager.R
import com.sd.passwordmanager.adapter.ItemAdapter
import com.sd.passwordmanager.adapter.Listener
import com.sd.passwordmanager.databinding.FragmentMainBinding
import com.sd.passwordmanager.dto.ItemPassword
import com.sd.passwordmanager.ui.CurrentFragment.Companion.textArg
import com.sd.passwordmanager.viewmodel.AuthViewModel
import com.sd.passwordmanager.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private var fragmentBinding: FragmentMainBinding? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        val adapter = ItemAdapter(object : Listener {
            override fun removeItem(item: ItemPassword) {
                viewModel.deleteItem(item)
            }

            override fun editItem(item: ItemPassword) {
                findNavController()
                    .navigate(R.id.action_mainFragment_to_currentFragment,
                        Bundle().apply {
                            textArg = item.id.toString()
                        })
            }

            override fun showPassword(item: ItemPassword) {
                viewModel.changeVisiblePassword(item)
            }

            override fun hidePassword(item: ItemPassword) {
                viewModel.changeVisiblePassword(item)
            }

        })

        binding.rwMain.layoutManager = LinearLayoutManager(activity)
        binding.rwMain.adapter = adapter

        viewModel.dataItem.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.menu.setOnClickListener {
            it.animTouch()
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_main)
                menu.setGroupVisible(R.id.unauthenticated, !authViewModel.authenticated)
                menu.setGroupVisible(R.id.authenticated, authViewModel.authenticated)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.signin -> {
                            authViewModel.changeShowUIfromAuth(1)
                            true
                        }

                        R.id.signup -> {
                            authViewModel.changeShowUIfromAuth(2)
                            true
                        }

                        R.id.signout -> {
                            areYouSureSignOut()
                            true
                        }

                        else -> false
                    }
                }
            }.show()
        }

        //UI в зависимости от аутентификации
        authViewModel.showUIfromAuth.observe(viewLifecycleOwner) {
            when (it) {
                //в системе
                0 -> {
                    binding.groupMain.isVisible = true
                    binding.cardViewSignup.isVisible = false
                    binding.cardViewSignIn.isVisible = false
                    binding.passwordSignin.setText("")
                    binding.passwordSignup.setText("")
                    binding.confirmPasswordSignup.setText("")
                }
                //войти
                1 -> {
                    binding.groupMain.isVisible = false
                    binding.cardViewSignup.isVisible = false
                    binding.cardViewSignIn.isVisible = true
                }
                //зарегистрироваться
                2 -> {
                    binding.groupMain.isVisible = false
                    binding.cardViewSignup.isVisible = true
                    binding.cardViewSignIn.isVisible = false
                }
                //диалог "войти или зарегистрироваться"
                3 -> {
                    binding.groupMain.isVisible = false
                    signInOrSignUp()
                }
                //не в системе
                4 -> {
                    viewModel.getAll(-1)
                    binding.groupMain.isVisible = false
                    binding.cardViewSignup.isVisible = false
                    binding.cardViewSignIn.isVisible = false
                }
            }
        }

        binding.fab.setOnClickListener {
            it.animTouch()
            findNavController()
                .navigate(R.id.action_mainFragment_to_currentFragment,
                    Bundle().apply {
                        textArg = "0"
                    })
        }

        binding.buttonCancelSignin.setOnClickListener {
            it.animTouch()
            binding.passwordSignin.setText("")
            authViewModel.changeShowUIfromAuth(4)
        }
        binding.buttonOkSignin.setOnClickListener {
            it.animTouch()
            if (isPasswordNotNull()) {
                authViewModel.signIn(binding.passwordSignin.text.toString())
            }
        }

        binding.buttonCancelSignup.setOnClickListener {
            it.animTouch()
            binding.passwordSignup.setText("")
            binding.confirmPasswordSignup.setText("")
            authViewModel.changeShowUIfromAuth(4)
        }

        binding.buttonOkSignup.setOnClickListener {
            it.animTouch()
            if (isPasswordCorrect()) {
                authViewModel.signUp(binding.passwordSignup.text.toString())
            }
        }

        //SignIn & SignUp
        authViewModel.stateAuth.observe(viewLifecycleOwner) {
            when (it) {
                // SignIn/SignUp Ok
                1 -> {
                    viewModel.getAll(authViewModel.data.value.id)
                    authViewModel.changeShowUIfromAuth(0)
                    authViewModel.changeStateAuth(0)
                }
                // в БД нет такого user'а - значит неверный мастер-пароль
                -1 -> {
                    showToast(getString(R.string.wrong_password))
                    authViewModel.changeStateAuth(0)
                }
                //новый user не записался в БД
                -2 -> {
                    showToast(getString(R.string.wrong_error))
                    authViewModel.changeStateAuth(0)
                }
                //такой user уже существует (для SignUp)
                -3 -> {
                    showToast(getString(R.string.accaunt_already_exists))
                    authViewModel.changeStateAuth(0)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun showToast(text: String) {
        Toast.makeText(
            requireActivity(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun areYouSureSignOut() {
        val menuDialog = SignInOutDialogFragment(
            title = getString(R.string.are_you_sure_you_want_to_logout),
            textPosButton = getString(R.string.yes),
            textNegButton = getString(R.string.no),
            flagSignOut = true,
        )
        val manager = childFragmentManager
        menuDialog.show(manager, "SignOut")
    }

    private fun signInOrSignUp() {
        val menuDialog = SignInOutDialogFragment(
            title = getString(R.string.you_arent_logged_into_your_accaunt),
            textPosButton = getString(R.string.signin),
            textNegButton = getString(R.string.signup),
            flagSignOut = false,
        )
        val manager = childFragmentManager
        menuDialog.show(manager, "SignIn SignUp")
    }

    private fun isPasswordNotNull(): Boolean {
        var flag = true

        fragmentBinding?.apply {
            if (passwordSignin.text.isNullOrEmpty()) {
                passwordSignin.error = getString(R.string.field_mustnt_empty)
                flag = false
            }
        }
        return flag
    }

    private fun isPasswordCorrect(): Boolean {
        var flag = true

        fragmentBinding?.apply {
            if (passwordSignup.text.isNullOrEmpty()) {
                passwordSignup.error = getString(R.string.field_mustnt_empty)
                flag = false
            }
            if (confirmPasswordSignup.text.isNullOrEmpty()) {
                confirmPasswordSignup.error = getString(R.string.field_mustnt_empty)
                flag = false
            }
            if (passwordSignup.text.toString() != confirmPasswordSignup.text.toString()) {
                passwordSignup.error = getString(R.string.password_doesnt_confirm)
                confirmPasswordSignup.error = getString(R.string.password_doesnt_confirm)
                flag = false
            }
        }
        return flag
    }

}