package com.ardianhilmip.catcares.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.data.local.entity.DataItem
import com.ardianhilmip.catcares.data.local.entity.RemoteKeys
import com.ardianhilmip.catcares.data.local.room.CatCaresDB
import com.ardianhilmip.catcares.data.remote.api.ApiService

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val database: CatCaresDB,
    private val service: ApiService,
    private val token: String
) : RemoteMediator<Int, DataItem>() {

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DataItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
        }

        try {
            val responseData = service.getArticle(
                token_auth = "Bearer $token",
                page,
                size = state.config.pageSize
            )

//            val endOfPaginationReached = responseData.data.isEmpty()
            val pageSize = state.config.pageSize
            val offset = (page - 1) * pageSize
            val endOfPaginationReached = responseData.data.size < offset

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.articleDao().clearAll()
                    database.remoteKeysDao().clearRemoteKeys()
                }
                val prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.data.map {
                    RemoteKeys(
                        id = it.artikelId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                database.remoteKeysDao().insertAll(keys)
                database.articleDao().insertArticle(responseData.data)
            }
            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.artikelId?.let { repoId ->
                database.remoteKeysDao().getRemoteKeysId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.artikelId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.pages.firstOrNull() { it.data.isNotEmpty()}?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.artikelId)
        }
    }


    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}