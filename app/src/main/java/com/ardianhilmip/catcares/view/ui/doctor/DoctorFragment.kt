package com.ardianhilmip.catcares.view.ui.doctor

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.factory.ViewModelFactory
import com.ardianhilmip.catcares.databinding.FragmentDoctorBinding
import com.ardianhilmip.catcares.view.adapter.LoadingStateAdapter
import com.ardianhilmip.catcares.view.adapter.doctor.DoctorListAdapter
import com.ardianhilmip.catcares.view.viewmodel.doctor.DoctorViewModel

class DoctorFragment : Fragment() {
    private var _binding : FragmentDoctorBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoctorBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDoctor()
        binding?.apply {
            rvDoctor.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun getDoctor() {
        val doctorViewModel: DoctorViewModel by viewModels() {
            ViewModelFactory (requireContext(), "${pref.getToken().token}")
        }

        val adapter = DoctorListAdapter()
        binding?.rvDoctor?.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter() {
                adapter.retry()
            }
        )

        doctorViewModel.doctor.observe(requireActivity()) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}