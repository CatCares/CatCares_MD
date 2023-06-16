package com.ardianhilmip.catcares.view.viewmodel.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.data.local.entity.DataItem
import com.ardianhilmip.catcares.data.repository.ArticleRepository

class ArticleViewModel(articleRepository: ArticleRepository): ViewModel() {
    val article: LiveData<PagingData<DataItem>> = articleRepository.getArticle().cachedIn(viewModelScope)
}