package com.ardianhilmip.catcares.view.ui.cat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.databinding.FragmentCatBinding
import com.ardianhilmip.catcares.utils.reduceFileImage
import com.ardianhilmip.catcares.utils.uriToFile
import com.ardianhilmip.catcares.view.ui.profile.SeeProfileFragment
import com.ardianhilmip.catcares.view.viewmodel.cat.CatViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CatFragment : Fragment() {
    private var _binding: FragmentCatBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private val viewModel: CatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding?.apply {
            btnAddPhoto.setOnClickListener {
                startTakeGalery()
            }
            btnUpload.setOnClickListener {
                upload()
            }
        }
    }

    private fun upload() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            binding?.apply {
                val name = inputNameCat.text.toString().trim()
                val ras = inputTypeCat.text.toString().trim()
                val gender = inputGenderCat.text.toString().trim()
                val age = inputAgeCat.text.toString().trim()
                val weight = inputWeightCat.text.toString().trim()
                val color = inputColorCat.text.toString().trim()
                when{
                    name.isEmpty() -> {
                        inputNameCat.error = getString(R.string.name_cat_field)
                        inputNameCat.requestFocus()
                    }
                    ras.isEmpty() -> {
                        inputTypeCat.error = getString(R.string.ras_cat_field)
                        inputTypeCat.requestFocus()
                    }
                    gender.isEmpty() -> {
                        inputGenderCat.error = getString(R.string.gender_cat_field)
                        inputGenderCat.requestFocus()
                    }
                    age.isEmpty() -> {
                        inputAgeCat.error = getString(R.string.age_cat_field)
                        inputAgeCat.requestFocus()
                    }
                    weight.isEmpty() -> {
                        inputWeightCat.error = getString(R.string.weight_cat_field)
                        inputWeightCat.requestFocus()
                    }
                    color.isEmpty() -> {
                        inputColorCat.error = getString(R.string.color_cat_field)
                        inputColorCat.requestFocus()
                    }
                    else -> {
                        val token_auth = "${pref.getToken().token}"
                        val name = name.toRequestBody("text/plain".toMediaType())
                        val ras = ras.toRequestBody("text/plain".toMediaType())
                        val gender = gender.toRequestBody("text/plain".toMediaType())
                        val age = age.toRequestBody("text/plain".toMediaType())
                        val weight = weight.toRequestBody("text/plain".toMediaType())
                        val color = color.toRequestBody("text/plain".toMediaType())
                        val fileImage = file.asRequestBody("image/*".toMediaType())
                        val foto = MultipartBody.Part?.createFormData("foto", file.name, fileImage)

                        viewModel.addCat(
                            token_auth,
                            name,
                            ras,
                            gender,
                            age,
                            weight,
                            color,
                            foto
                        )

                        viewModel.message.observe(viewLifecycleOwner) { message ->
                            if (message == "Kucing berhasil dibuat") {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setIcon(R.drawable.ic_baseline_check_24)
                                builder.setTitle(getString(R.string.Info))
                                builder.setMessage(getString(R.string.add_cat_success))
                                val alertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    alertDialog.dismiss()
                                }, UPLOAD_TIME_DELAY)
                            } else {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setIcon(R.drawable.ic_baseline_close_24)
                                builder.setTitle(getString(R.string.Info))
                                builder.setMessage(getString(R.string.add_cat_failed))
                                val alertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    alertDialog.dismiss()
                                }, SeeProfileFragment.UPDATE_TIME_DELAY)
                            }
                        }

                        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
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

    private fun startTakeGalery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val selectImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectImg, requireContext())
            getFile = myFile
            binding?.imgProfile?.setImageURI(selectImg)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val UPLOAD_TIME_DELAY = 2000L
    }
}