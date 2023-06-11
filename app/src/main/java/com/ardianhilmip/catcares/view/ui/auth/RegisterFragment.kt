package com.ardianhilmip.catcares.view.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.databinding.FragmentRegisterBinding
import com.ardianhilmip.catcares.view.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupViewModel()
        setupAction()

        binding?.apply {
            textToLogin.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return binding?.root
    }

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        registerViewModel.error.observe(viewLifecycleOwner) {error ->
            registerViewModel.status.observe(viewLifecycleOwner) {status ->
                if(status == 201) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setIcon(R.drawable.ic_baseline_check_24)
                    builder.setTitle(getString(R.string.Info))
                    builder.setMessage(getString(R.string.register_success))
                    val alertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                        view?.post {
                            findNavController().popBackStack()
                        }
                    }, REGISTER_TIME_OUT)
                } else {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setIcon(R.drawable.ic_baseline_close_24)
                    builder.setTitle(getString(R.string.Info))
                    builder.setMessage(getString(R.string.register_failed))
                    val alertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        alertDialog.dismiss()
                    }, REGISTER_TIME_OUT)
                }
            }
        }

        registerViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding?.apply {
                if(loading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setupAction() {
        binding?.apply {
            btnRegister.setOnClickListener {
                val firstname = inputFirstName.text.toString().trim()
                val lastname = inputLastName.text.toString().trim()
                val email = inputEmail.text.toString().trim()
                val password = inputPassword.text.toString().trim()
                val confPassword = inputKonfPassword.text.toString().trim()
                when {
                    firstname.isEmpty() -> {
                        inputFirstName.error = getString(R.string.name_required)
                        inputFirstName.requestFocus()
                    }
                    lastname.isEmpty() -> {
                        inputLastName.error = getString(R.string.name_required)
                        inputLastName.requestFocus()
                    }
                    email.isEmpty() -> {
                        inputEmail.error = getString(R.string.email_required)
                        inputEmail.requestFocus()
                    }
                    password.isEmpty() -> {
                        inputPassword.error = getString(R.string.password_required)
                        inputPassword.requestFocus()
                    }
                    confPassword.isEmpty() -> {
                        inputKonfPassword.error = getString(R.string.password_required)
                        inputKonfPassword.requestFocus()
                    }
                    !password.equals(confPassword) -> {
                        inputKonfPassword.error = getString(R.string.password_doesnt_match)
                        inputKonfPassword.requestFocus()
                    }
                    else -> {
                        registerViewModel.register(firstname, lastname, email, password, confPassword)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val REGISTER_TIME_OUT = 2000L
    }
}