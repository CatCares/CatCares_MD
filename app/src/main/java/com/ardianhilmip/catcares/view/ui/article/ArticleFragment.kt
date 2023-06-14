package com.ardianhilmip.catcares.view.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardianhilmip.catcares.data.UserPreference
import com.ardianhilmip.catcares.data.factory.ViewModelFactory
import com.ardianhilmip.catcares.data.local.Article.Article
import com.ardianhilmip.catcares.data.local.Article.FakeArticleDataSource
import com.ardianhilmip.catcares.databinding.FragmentArticleBinding
import com.ardianhilmip.catcares.view.adapter.BannerArticleAdapter
import com.ardianhilmip.catcares.view.adapter.ArticleListAdapter
import com.ardianhilmip.catcares.view.adapter.LoadingStateAdapter
import com.ardianhilmip.catcares.view.viewmodel.article.ArticleViewModel

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding
    private val pref: UserPreference by lazy {
        UserPreference(requireContext())
    }
    private val listBanner = ArrayList<Article>()

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
        listBanner.addAll(getBanner())
        binding?.apply {
            listArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            bannerArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                val bannerArticleAdapter = BannerArticleAdapter(listBanner)
                adapter = bannerArticleAdapter
            }
        }
    }

    private fun getArticle() {
        val articleViewModel: ArticleViewModel by viewModels() {
            ViewModelFactory (requireContext(), "${pref.getToken().token}")
        }

        val adapter = ArticleListAdapter()
        binding?.listArticle?.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter() {
                adapter.retry()
            }
        )

        articleViewModel.article.observe(requireActivity()) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun getBanner(): ArrayList<Article> {
        val listBanner = ArrayList<Article>()
        FakeArticleDataSource.dummyArticle.size.let {
            for (i in 0 until 2) {
                val article = Article(
                    FakeArticleDataSource.dummyArticle[i].id,
                    FakeArticleDataSource.dummyArticle[i].title,
                    FakeArticleDataSource.dummyArticle[i].image,
                    FakeArticleDataSource.dummyArticle[i].createAt,
                )
                listBanner.add(article)
            }
        }
        return listBanner
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}