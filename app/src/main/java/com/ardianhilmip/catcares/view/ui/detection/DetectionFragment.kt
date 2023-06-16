package com.ardianhilmip.catcares.view.ui.detection

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
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
import com.ardianhilmip.catcares.databinding.FragmentDetectionBinding
import com.ardianhilmip.catcares.utils.createCustomTempFile
import com.ardianhilmip.catcares.utils.reduceFileImage
import com.ardianhilmip.catcares.utils.uriToFile
import com.ardianhilmip.catcares.view.ui.profile.SeeProfileFragment
import com.ardianhilmip.catcares.view.viewmodel.prediction.PredictionViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class DetectionFragment : Fragment() {
    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private val viewModel: PredictionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentDetectionBinding.inflate(inflater, container, false)
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
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnCamera.setOnClickListener {
                startTakePhoto()
            }
            btnGalery.setOnClickListener {
                startTakeGalery()
            }
            btnDetect.setOnClickListener {
                predection()
            }
        }
    }

    private fun predection() {
        if (getFile != null) {
            val token_auth = "${pref.getToken().token}"
            val file = reduceFileImage(getFile as File)
            val fileImage = file.asRequestBody("image/*".toMediaType())
            val image = MultipartBody.Part.createFormData("image", file.name, fileImage)

            viewModel.prediction(
                token_auth,
                image
            )

            viewModel.isRingworm.observe(viewLifecycleOwner) { isRingworm ->
                if (isRingworm == true) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        view?.post {
                            findNavController().navigate(R.id.action_detectionFragment_to_terdeteksiFragment)
                        }
                    }, DETECTION_TIME)
                }
                if (isRingworm == false) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        view?.post {
                            findNavController().navigate(R.id.action_detectionFragment_to_amanFragment)
                        }
                    }, DETECTION_TIME)
                }
            }

            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    binding?.apply {
                        progressBar.visibility = View.VISIBLE
                    }
                } else {
                    binding?.apply {
                        progressBar.visibility = View.GONE
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
            binding?.ivItemPhoto?.setImageURI(selectImg)
        }
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
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let {
                getFile = it
                binding?.ivItemPhoto?.setImageBitmap(BitmapFactory.decodeFile(it.path))
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val DETECTION_TIME = 2000L
    }
}