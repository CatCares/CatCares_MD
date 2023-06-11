package com.ardianhilmip.catcares.view.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.local.Article
import com.ardianhilmip.catcares.data.local.FakeArticleDataSource
import com.ardianhilmip.catcares.databinding.FragmentArticleBinding
import com.ardianhilmip.catcares.view.adapter.BannerArticleAdapter
import com.ardianhilmip.catcares.view.adapter.ListArticleAdapter

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding
    private val list = ArrayList<Article>()
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
        list.addAll(getArticle())
        listBanner.addAll(getBanner())
        binding?.apply {
            listArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                val articleAdapter = ListArticleAdapter(list)
                adapter = articleAdapter
            }
            bannerArticle.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                val bannerArticleAdapter = BannerArticleAdapter(listBanner)
                adapter = bannerArticleAdapter
            }
        }
    }

    private fun getArticle(): ArrayList<Article> {
        val listArticle = ArrayList<Article>()
        FakeArticleDataSource.dummyArticle.forEach {
            val article = Article(
                it.id,
                it.title,
                it.image,
                it.createAt,
            )
            listArticle.add(article)
        }
        return listArticle
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