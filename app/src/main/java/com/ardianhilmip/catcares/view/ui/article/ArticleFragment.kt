package com.ardianhilmip.catcares.view.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.article.ArticleResponse
import com.ardianhilmip.catcares.databinding.FragmentArticleBinding
import com.ardianhilmip.catcares.view.adapter.article.BannerArticleAdapter
import com.ardianhilmip.catcares.view.adapter.article.ArticleVerticalAdapter
import retrofit2.Callback

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }
    private val listArticle = ArrayList<ArticleResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArticle()
        getBanner()
        binding?.apply {
            listArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
            bannerArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun getArticle() {
        val token_auth = "Bearer ${pref.getToken().token}"

        ApiConfig.getApiService().getListArticle(token_auth).enqueue(object :
            Callback<ArrayList<ArticleResponse>> {
            override fun onResponse(
                call: retrofit2.Call<ArrayList<ArticleResponse>>,
                response: retrofit2.Response<ArrayList<ArticleResponse>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()
                    if (list != null) {
                        listArticle.addAll(list)
                        binding?.listArticle?.adapter = ArticleVerticalAdapter(listArticle)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<ArticleResponse>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun getBanner() {
        val token_auth = "Bearer ${pref.getToken().token}"

        ApiConfig.getApiService().getListArticle(token_auth).enqueue(object :
            Callback<ArrayList<ArticleResponse>> {
            override fun onResponse(
                call: retrofit2.Call<ArrayList<ArticleResponse>>,
                response: retrofit2.Response<ArrayList<ArticleResponse>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()
                    if (list != null) {
                        listArticle.addAll(list)
                        binding?.bannerArticle?.adapter = BannerArticleAdapter(listArticle)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<ArticleResponse>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}