package com.ardianhilmip.catcares.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.databinding.FragmentHomeBinding
import com.ardianhilmip.catcares.data.factory.ViewModelFactory
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.view.adapter.ArticleListAdapter
import com.ardianhilmip.catcares.view.adapter.LoadingStateAdapter
import com.ardianhilmip.catcares.view.adapter.doctor.DoctorListAdapter
import com.ardianhilmip.catcares.view.viewmodel.article.ArticleViewModel
import com.ardianhilmip.catcares.view.viewmodel.doctor.DoctorViewModel
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDoctor()
//        getArticle()
        getName()
        binding?.apply {
            rvDoctor.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            rvArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            btnLihatDoctor.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDoctorFragment2())
            }
        }
    }

    private fun getName() {
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
                Log.e("HomeFragement", "onFailure: ${t.message}")
            }
        })
    }

//    private fun getArticle() {
//        val articleViewModel: ArticleViewModel by viewModels() {
//            ViewModelFactory (requireContext(), "${pref.getToken().token}")
//        }
//
//        val adapter = ArticleListAdapter()
//        binding?.rvArticle?.adapter = adapter.withLoadStateFooter(
//            footer = LoadingStateAdapter() {
//                adapter.retry()
//            }
//        )
//
//        articleViewModel.article.observe(requireActivity()) {
//            adapter.submitData(lifecycle, it)
//        }
//    }

    private fun getDoctor() {
        val doctorViewModel: DoctorViewModel by viewModels() {
            ViewModelFactory (requireContext(), "${pref.getToken().token}")
        }
        val adapter = DoctorListAdapter()
        binding?.rvDoctor?.adapter = adapter

        doctorViewModel.doctor.observe(requireActivity()) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}