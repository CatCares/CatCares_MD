package com.ardianhilmip.catcares.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardianhilmip.catcares.di.Injection
import com.ardianhilmip.catcares.view.viewmodel.article.ArticleViewModel

class ArticleModelFactory (private val context: Context, val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleViewModel(Injection.articleRepository(context, token)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}