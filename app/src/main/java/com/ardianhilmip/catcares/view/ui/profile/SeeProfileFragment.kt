package com.ardianhilmip.catcares.view.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.databinding.FragmentSeeProfileBinding
import com.ardianhilmip.catcares.utils.createCustomTempFile
import com.ardianhilmip.catcares.utils.reduceFileImage
import com.ardianhilmip.catcares.view.ui.auth.RegisterFragment
import com.ardianhilmip.catcares.view.viewmodel.profile.UpdateViewModel
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import java.io.File

class SeeProfileFragment : Fragment() {
    private var _binding: FragmentSeeProfileBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private val updateViewModel: UpdateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeeProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        binding?.apply {
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnUpdate.setOnClickListener {
                updateUser()
            }
            btnAddPhoto.setOnClickListener {
                startTakePhoto()
            }
        }
        getProfile()
    }

    private fun startTakePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireContext()).also {
            val photoUri: Uri = FileProvider.getUriForFile(
                requireActivity(), "com.ardianhilmip.catcares", it
            )
            currentPhotoPath = it.absolutePath
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(takePictureIntent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
                getFile = file
                binding?.imgProfile?.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(), getString(R.string.not_permission), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }


    private fun updateUser() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            binding?.apply {
                val firstname = inputFirstName.text.toString().trim()
                val lastname = inputLastName.text.toString().trim()
                val phone = inputPhone.text.toString().trim()
                val address = inputAddress.text.toString().trim()
                when {
                    firstname.isEmpty() -> {
                        inputFirstName.error = getString(R.string.name_required)
                        inputFirstName.requestFocus()
                    }
                    lastname.isEmpty() -> {
                        inputLastName.error = getString(R.string.name_required)
                        inputLastName.requestFocus()
                    }
                    phone.isEmpty() -> {
                        inputPhone.error = getString(R.string.phone_required)
                        inputPhone.requestFocus()
                    }
                    address.isEmpty() -> {
                        inputAddress.error = getString(R.string.address_required)
                        inputAddress.requestFocus()
                    }
                    else -> {
                        val token_auth = "${pref.getToken().token}"
                        val firsName = firstname.toRequestBody("text/plain".toMediaType())
                        val lastName = lastname.toRequestBody("text/plain".toMediaType())
                        val phoneNumber = phone.toRequestBody("text/plain".toMediaType())
                        val addressUser = address.toRequestBody("text/plain".toMediaType())
                        val fileImage = file.asRequestBody("image/*".toMediaType())
                        val foto = MultipartBody.Part?.createFormData("foto", file.name, fileImage)

                        updateViewModel.update(
                            token_auth,
                            firsName,
                            lastName,
                            addressUser,
                            phoneNumber,
                            foto
                        )

                        updateViewModel.error.observe(viewLifecycleOwner) { error ->
                            updateViewModel.message.observe(viewLifecycleOwner) { message ->
                                if (message == getString(R.string.update_success)) {
                                    val builder = AlertDialog.Builder(requireContext())
                                    builder.setIcon(R.drawable.ic_baseline_check_24)
                                    builder.setTitle(getString(R.string.Info))
                                    builder.setMessage(getString(R.string.update_profil_success))
                                    val alertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        alertDialog.dismiss()
                                        view?.post {
                                            findNavController().popBackStack()
                                        }
                                    }, UPDATE_TIME_DELAY)
                                } else {
                                    val builder = AlertDialog.Builder(requireContext())
                                    builder.setIcon(R.drawable.ic_baseline_close_24)
                                    builder.setTitle(getString(R.string.Info))
                                    builder.setMessage(getString(R.string.update_profil_failed))
                                    val alertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        alertDialog.dismiss()
                                    }, UPDATE_TIME_DELAY)
                                }
                            }
                        }

                        updateViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
                            if (loading) {
                                binding?.progressBar?.visibility = View.VISIBLE
                            } else {
                                binding?.progressBar?.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
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
                        inputFirstName .setText(data?.firstName)
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
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val UPDATE_TIME_DELAY = 2000L
    }
}