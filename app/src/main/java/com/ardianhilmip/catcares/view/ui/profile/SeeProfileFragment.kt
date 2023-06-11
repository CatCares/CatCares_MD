package com.ardianhilmip.catcares.view.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.databinding.FragmentSeeProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class SeeProfileFragment : Fragment() {
    private var _binding: FragmentSeeProfileBinding? = null
    private val binding get() = _binding
//    private lateinit var auth: FirebaseAuth
//    private lateinit var databaseReference: DatabaseReference
//    private lateinit var uid: String
//    private lateinit var imageUri: Uri
//    private val baos = ByteArrayOutputStream()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeeProfileBinding.inflate(inflater, container, false)

//        auth = FirebaseAuth.getInstance()
//        uid = auth.currentUser?.uid.toString()
//        databaseReference = FirebaseDatabase.getInstance().getReference("users")
//        if (uid.isNotEmpty()) { getUserData() }
//
        binding?.apply {
//            if (auth.currentUser?.photoUrl != null) {
//                Picasso.get().load(auth.currentUser?.photoUrl).into(imgProfile)
//            } else imgProfile.setImageResource(R.drawable.ic_baseline_account_circle)
//            btnAddPhoto.setOnClickListener {
//                intentCamera()
//            }
//            btnUpdate.setOnClickListener {
//                updateProfile()
//            }
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
//            inputEmail.isEnabled = false
        }
        return binding?.root
    }

//    private fun updateProfile() {
//        val name = binding?.inputName?.text.toString()
//        val noHp = binding?.inputNoHp?.text.toString()
//        val address = binding?.inputAddress?.text.toString()
//        val email = binding?.inputEmail?.text.toString()
//        val image = when {
//            ::imageUri.isInitialized -> imageUri
//            auth.currentUser?.photoUrl != null -> auth.currentUser?.photoUrl
//            else -> Uri.parse(
//                "android.resource://com.ardianhilmip.catcares./drawable/ic_baseline_account_circle"
//            )
//        }
//
//        val user = User(name, noHp, address, email)
//        databaseReference.child(uid).setValue(user).addOnCompleteListener {
//            if (it.isSuccessful) {
//                UserProfileChangeRequest.Builder()
//                    .setPhotoUri(image)
//                    .build()
//                    .also { request ->
//                        auth.currentUser?.updateProfile(request)
//                    }
//                Toast.makeText(requireContext(), getString(R.string.update_profil_success), Toast.LENGTH_SHORT)
//                    .show()
//                findNavController().popBackStack()
//            } else {
//                Toast.makeText(requireContext(), getString(R.string.update_profil_failed), Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//
//    private fun intentCamera() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
//            intent.resolveActivity(requireActivity().packageManager)?.also {
//                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE) {
//            if (resultCode == AppCompatActivity.RESULT_OK) {
//                val imageBitmap = data?.extras?.get("data") as Bitmap
//                uploadImage(imageBitmap)
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun uploadImage(imageBitmap: Bitmap) {
//        val ref =
//            FirebaseStorage.getInstance().reference.child("images/${FirebaseAuth.getInstance().currentUser?.uid}")
//
//        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val image = baos.toByteArray()
//
//        ref.putBytes(image).addOnCompleteListener {
//            if (it.isSuccessful) {
//                ref.downloadUrl.addOnCompleteListener { url ->
//                    imageUri = url.result!!
//                    binding?.imgProfile?.setImageBitmap(imageBitmap)
//                }
//            }
//        }
//    }
//
//    private fun getUserData() {
//        databaseReference.child(uid).get().addOnSuccessListener {
//            binding?.apply {
//                inputName.apply {
//                    setText(it.child("name").value.toString())
//                    isEnabled = true
//                }
//                inputNoHp.apply {
//                    setText(it.child("noHp").value.toString())
//                    isEnabled = true
//                }
//                inputAddress.apply {
//                    setText(it.child("address").value.toString())
//                    isEnabled = true
//                }
//                inputEmail.apply {
//                    setText(it.child("email").value.toString())
//                    isEnabled = false
//                }
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
    }
}