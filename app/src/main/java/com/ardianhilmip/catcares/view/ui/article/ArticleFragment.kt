package com.ardianhilmip.catcares.view.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.factory.ArticleModelFactory
import com.ardianhilmip.catcares.databinding.FragmentArticleBinding
import com.ardianhilmip.catcares.view.adapter.LoadingStateAdapter
import com.ardianhilmip.catcares.view.adapter.article.ArticleAdapter
import com.ardianhilmip.catcares.view.adapter.article.BannerArticleAdapter
import com.ardianhilmip.catcares.view.viewmodel.article.ArticleViewModel

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }

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
            }
            bannerArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun getArticle() {
        val articleViewModel: ArticleViewModel by viewModels() {
            ArticleModelFactory(requireContext(), "${pref.getToken().token}")
        }

        val adapter = ArticleAdapter()
        binding?.listArticle?.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter() {
                adapter.retry()
            }
        )
        articleViewModel.article.observe(requireActivity()) {
            adapter.submitData(requireActivity().lifecycle, it)
        }
    }
    private fun getBanner() {
        val articleViewModel: ArticleViewModel by viewModels() {
            ArticleModelFactory(requireContext(), "${pref.getToken().token}")
        }

        val adapter = BannerArticleAdapter()
        binding?.bannerArticle?.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter() {
                adapter.retry()
            }
        )
        articleViewModel.article.observe(requireActivity()) {
            adapter.submitData(requireActivity().lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}