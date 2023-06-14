package com.ardianhilmip.catcares.view.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.databinding.FragmentSeeProfileBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class SeeProfileFragment : Fragment() {
    private var _binding: FragmentSeeProfileBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeeProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            imgBack.setOnClickListener {
                findNavController().popBackStack()
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
                        inputName.setText(data?.firstName)
                        inputLastName.setText(data?.lastName)
                        inputPhone.setText(data?.noHP)
                        inputAddress.setText(data?.alamat)
                        Glide.with(requireContext())
                            .load(data?.foto)
                            .into(imgProfile)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("SeeProfileFragment", "onFailure: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
    }
}