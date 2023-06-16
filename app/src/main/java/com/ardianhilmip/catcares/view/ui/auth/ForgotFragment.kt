package com.ardianhilmip.catcares.view.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.databinding.FragmentForgotBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotFragment : Fragment() {
    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)


        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}