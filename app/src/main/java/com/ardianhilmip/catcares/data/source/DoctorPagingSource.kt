package com.ardianhilmip.catcares.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.data.remote.api.ApiService

class DoctorPagingSource(
    private val service: ApiService,
    private val token: String
) : PagingSource<Int, DataDoctor>() {

    override fun getRefreshKey(state: PagingState<Int, DataDoctor>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataDoctor> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = service.getListDoctor(token_auth = "Bearer $token", page, params.loadSize).data

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