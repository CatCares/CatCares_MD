package com.ardianhilmip.catcares.view.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.local.User
import com.ardianhilmip.catcares.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding?.apply {
            textToLogin.setOnClickListener {
                findNavController().popBackStack()
            }
            btnRegister.setOnClickListener {
                val name = inputName.text.toString().trim()
                val noHp = inputPhone.text.toString().trim()
                val address = inputAddress.text.toString().trim()
                val email = inputEmail.text.toString().trim()
                val password = inputPassword.text.toString().trim()
                val confPassword = inputKonfPassword.text.toString().trim()

                progressBar.visibility = View.VISIBLE

                if (name.isEmpty() || noHp.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    if (name.isEmpty()) {
                        inputName.error = getString(R.string.name_required)
                        inputName.requestFocus()
                    }
                    if (noHp.isEmpty()) {
                        inputPhone.error = getString(R.string.phone_required)
                        inputPhone.requestFocus()
                    }
                    if (address.isEmpty()) {
                        inputAddress.error = getString(R.string.address_required)
                        inputAddress.requestFocus()
                    }
                    if (email.isEmpty()) {
                        inputEmail.error = getString(R.string.email_required)
                        inputEmail.requestFocus()
                    }
                    if (password.isEmpty()) {
                        inputPassword.error = getString(R.string.password_required)
                        inputPassword.requestFocus()
                    }
                } else if (!password.equals(confPassword)) {
                    inputKonfPassword.error = getString(R.string.password_doesnt_match)
                    inputKonfPassword.requestFocus()
                    return@setOnClickListener
                } else {
                    registerUser(email, password, name, noHp, address)
                }
            }
        }
        return binding?.root
    }

    private fun registerUser(
        email: String,
        password: String,
        name: String,
        noHp: String,
        address: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val databaseRef =
                        database.reference.child("users").child(auth.currentUser?.uid.toString())
                    val users: User =
                        User(name, noHp, address, email, auth.currentUser?.uid.toString())

                    databaseRef.setValue(users).addOnCompleteListener {
                        if (it.isSuccessful) {
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.register_success),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    binding?.apply {
                        inputEmail.error = getString(R.string.email_already_registered)
                        inputEmail.requestFocus()
                    }
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}