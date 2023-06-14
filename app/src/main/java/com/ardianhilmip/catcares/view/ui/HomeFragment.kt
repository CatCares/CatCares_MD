package com.ardianhilmip.catcares.view.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.SettingPreference
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.local.Article.Article
import com.ardianhilmip.catcares.data.local.Article.FakeArticleDataSource
import com.ardianhilmip.catcares.data.local.Doctor.Doctor
import com.ardianhilmip.catcares.data.local.Doctor.FakeDoctorDataSource
import com.ardianhilmip.catcares.data.local.History.FakeHistoryDataSource
import com.ardianhilmip.catcares.data.local.History.History
import com.ardianhilmip.catcares.databinding.FragmentHomeBinding
import com.ardianhilmip.catcares.data.factory.SettingModelFactory
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.view.adapter.DoctorAdapter
import com.ardianhilmip.catcares.view.adapter.HistoryAdapter
import com.ardianhilmip.catcares.view.adapter.ListArticleAdapter
import com.ardianhilmip.catcares.view.viewmodel.SettingViewModel
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val setting: SettingPreference by lazy {
        SettingPreference.getInstance(requireContext().dataStore)
    }
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }
    private val listHistory = ArrayList<History>()
    private val listDoctor = ArrayList<Doctor>()
    private val listArticle = ArrayList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listHistory.addAll(getHistory())
        listDoctor.addAll(getDoctor())
        listArticle.addAll(getArticle())
        getName()
        binding?.apply {
            rvSchedule.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                val historyAdapter = HistoryAdapter(listHistory)
                adapter = historyAdapter
            }
            rvDoctor.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                val doctorAdapter = DoctorAdapter(listDoctor)
                adapter = doctorAdapter
            }
            rvArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                val articleAdapter = ListArticleAdapter(listArticle)
                adapter = articleAdapter
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

    private fun getArticle(): ArrayList<Article> {
        val listArticle = ArrayList<Article>()
        FakeArticleDataSource.dummyArticle.size.let {
            for (i in 0 until 3) {
                val article = Article(
                    FakeArticleDataSource.dummyArticle[i].id,
                    FakeArticleDataSource.dummyArticle[i].title,
                    FakeArticleDataSource.dummyArticle[i].image,
                    FakeArticleDataSource.dummyArticle[i].createAt,
                )
                listArticle.add(article)
            }
        }
        return listArticle
    }

    private fun getDoctor(): ArrayList<Doctor> {
        val listDoctor = ArrayList<Doctor>()
        FakeDoctorDataSource.dummyDoctor.size.let {
            for (i in 0 until 2) {
                val doctor = Doctor(
                    FakeDoctorDataSource.dummyDoctor[i].id,
                    FakeDoctorDataSource.dummyDoctor[i].name,
                    FakeDoctorDataSource.dummyDoctor[i].photo,
                    FakeDoctorDataSource.dummyDoctor[i].location,
                    FakeDoctorDataSource.dummyDoctor[i].specialist,
                )
                listDoctor.add(doctor)
            }
        }
        return listDoctor
    }

    private fun getHistory(): ArrayList<History> {
        val listHistory = ArrayList<History>()
        FakeHistoryDataSource.dummyHistory[0].let {
            val history = History(
                it.id,
                it.name,
                it.photo,
                it.specialist,
                it.time,
                it.date
            )
            listHistory.add(history)
        }
        return listHistory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingViewModel = ViewModelProvider(requireActivity(), SettingModelFactory(setting)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(requireActivity()) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}