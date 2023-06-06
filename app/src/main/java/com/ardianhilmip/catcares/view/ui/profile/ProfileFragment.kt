package com.ardianhilmip.catcares.view.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.databinding.FragmentProfileBinding
import com.ardianhilmip.catcares.view.ui.HomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        if (uid.isNotEmpty()) { getNameUser() }

        binding?.apply {
            if (auth.currentUser?.photoUrl != null) {
                Picasso.get().load(auth.currentUser?.photoUrl).into(imgProfile)
            } else {
                binding?.imgProfile?.setImageResource(R.drawable.ic_baseline_account_circle)
            }
            btnLogout.setOnClickListener {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(getString(R.string.logout))
                    setMessage(getString(R.string.logout_confirmation))
                    setPositiveButton(getString(R.string.yes)) { _, _ ->
                        auth.signOut()
                        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                    }
                }.show()
            }
            tvLihatProfil.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSeeProfileFragment())
            }
            tvName.text = auth.currentUser?.displayName
        }

        return binding?.root
    }

    private fun getNameUser() {
        databaseReference.child(uid).get().addOnSuccessListener {
            binding?.tvName?.text = it.child("name").value.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}