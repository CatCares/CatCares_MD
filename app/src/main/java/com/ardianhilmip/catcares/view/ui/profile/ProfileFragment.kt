package com.ardianhilmip.catcares.view.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.databinding.FragmentProfileBinding
import com.ardianhilmip.catcares.view.ui.HomeFragmentDirections
import com.ardianhilmip.catcares.view.ui.profile.theme.ThemeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        binding?.apply {
            btnLogout.setOnClickListener {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(getString(R.string.logout))
                    setMessage(getString(R.string.logout_confirmation))
                    setPositiveButton(getString(R.string.yes)) { _, _ ->
                        pref.logOut()
                        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                    }
                }.show()
            }
            tvLihatProfil.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSeeProfileFragment())
            }
            btnTheme.setOnClickListener {
                val showPopUp = ThemeFragment()
                showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showTheme")
            }
        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}