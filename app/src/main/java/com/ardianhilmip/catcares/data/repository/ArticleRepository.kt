package com.ardianhilmip.catcares.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ardianhilmip.catcares.data.local.entity.ArticleDataItem
import com.ardianhilmip.catcares.data.local.room.CatCaresDB
import com.ardianhilmip.catcares.data.remote.api.ApiService
import com.ardianhilmip.catcares.data.remote.mediator.article.ArticleRemoteMediator

class ArticleRepository(
    private val database: CatCaresDB,
    private val service: ApiService,
    private val token: String
) {
    fun getArticle(): LiveData<PagingData<ArticleDataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 2
            ),
            remoteMediator = ArticleRemoteMediator(database, service, token),
            pagingSourceFactory = {
                database.articleDao().getAllArticle()
            }
        ).liveData
    }
}