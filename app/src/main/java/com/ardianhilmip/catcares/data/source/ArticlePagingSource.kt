package com.ardianhilmip.catcares.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ardianhilmip.catcares.data.remote.api.ApiService
import com.ardianhilmip.catcares.data.remote.response.article.ArticleResponseItem

class ArticlePagingSource(
    private val service: ApiService,
    private val token: String
) : PagingSource<Int, ArticleResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponseItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = service.getListArticle(token_auth = "Bearer $token", page, params.loadSize).articleResponse

            LoadResult.Page(
                data = responseData,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}