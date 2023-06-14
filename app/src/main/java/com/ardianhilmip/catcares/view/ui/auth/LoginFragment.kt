package com.ardianhilmip.catcares.view.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.remote.response.auth.User
import com.ardianhilmip.catcares.databinding.FragmentLoginBinding
import com.ardianhilmip.catcares.view.viewmodel.auth.LoginViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupLogin()
        setupViewModel()
        binding?.apply {
            textToRegis.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
            lupaPassword.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotFragment())
            }
        }

        return binding?.root
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        loginViewModel.error.observe(viewLifecycleOwner) { error ->
            loginViewModel.status.observe(viewLifecycleOwner) { status ->
                if (status == 200) {
                    loginViewModel.loginData.observe(viewLifecycleOwner) { dataLogin ->
                        val token = dataLogin.token

                        UserPreference(requireContext()).apply {
                            setToken(User(token))
                        }
                    }
                    val Builder = AlertDialog.Builder(requireContext())
                    Builder.setIcon(R.drawable.ic_baseline_check_24)
                    Builder.setTitle(getString(R.string.Info))
                    Builder.setMessage(getString(R.string.login_success))
                    val alertDialog = Builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                        view?.post {
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        }
                    }, LOGIN_TIME_OUT)
                } else {
                    val Builder = AlertDialog.Builder(requireContext())
                    Builder.setIcon(R.drawable.ic_baseline_close_24)
                    Builder.setTitle(getString(R.string.Info))
                    Builder.setMessage(getString(R.string.login_failed))
                    val alertDialog = Builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                    }, LOGIN_TIME_OUT)
                }
            }
        }

        loginViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.apply {
                if (isLoading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setupLogin() {
        binding?.apply {
            btnLogin.setOnClickListener {
                val email = inputEmail.text.toString().trim()
                val password = inputPassword.text.toString().trim()

                when {
                    email.isEmpty() -> {
                        inputEmail.error = getString(R.string.email_required)
                        inputEmail.requestFocus()
                    }
                    password.isEmpty() -> {
                        inputPassword.error = getString(R.string.password_required)
                        inputPassword.requestFocus()
                    }
                    password.length < 6 -> {
                        inputPassword.error = getString(R.string.invalid_password)
                        inputPassword.requestFocus()
                    }
                    else -> loginViewModel.login(email, password)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val LOGIN_TIME_OUT = 3000L
    }
}