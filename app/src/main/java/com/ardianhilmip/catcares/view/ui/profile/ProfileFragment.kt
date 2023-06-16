package com.ardianhilmip.catcares.view.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.databinding.FragmentProfileBinding
import com.ardianhilmip.catcares.view.ui.profile.theme.ThemeFragment
import com.ardianhilmip.catcares.view.viewmodel.doctor.DoctorViewModel
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            btnLocationDoctor.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToMapsFragment())
            }
            btnLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }

        getProfile()
    }

    private fun getProfile() {
        val token_auth = "Bearer ${pref.getToken().token}"

        val client = ApiConfig.getApiService().getUser(token_auth)
        client.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    binding?.apply {
                        tvName.setText(data?.firstName + " " + data?.lastName)
                        Glide.with(requireContext())
                            .load(data?.foto)
                            .into(imgProfile)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("ProfileFragment", "onFailure: ${t.message}")
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}