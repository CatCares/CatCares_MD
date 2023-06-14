package com.ardianhilmip.catcares.view.ui.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.databinding.FragmentDetailDoctorBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class DetailDoctorFragment : Fragment() {
    private var _binding : FragmentDetailDoctorBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding?.apply {
            nameDoctor.text = arguments?.getString(NAME)
            tvSpesialist.text = arguments?.getString(SPESIALIST)
            dataAddress.apply {
                setText(arguments?.getString(ADDRESS))
                isFocusable = false
            }
            dataPhone.setText(arguments?.getString(PHONE))
            dataEmail.setText(arguments?.getString(EMAIL))
            Glide.with(requireContext())
                .load(arguments?.getString(PHOTO_URL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgProfile)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailDoctorBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NAME = "name"
        const val SPESIALIST = "spesialist"
        const val ADDRESS = "address"
        const val PHONE = "phone"
        const val PHOTO_URL = "photoUrl"
        const val EMAIL = "email"
    }
}