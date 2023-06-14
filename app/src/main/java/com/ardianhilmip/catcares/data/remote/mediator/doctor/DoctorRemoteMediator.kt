package com.ardianhilmip.catcares.data.remote.mediator.doctor

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.data.local.entity.RemoteKeys
import com.ardianhilmip.catcares.data.local.room.CatCaresDB
import com.ardianhilmip.catcares.data.remote.api.ApiService

@OptIn(ExperimentalPagingApi::class)
class DoctorRemoteMediator(
    private val database: CatCaresDB,
    private val service: ApiService,
    private val token: String
) : RemoteMediator<Int, DataDoctor>() {

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DataDoctor>
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
            val responseData = service.getListDoctor(
                token_auth = "Bearer $token",
                page,
                size = state.config.pageSize
            )

            val endOfPaginationReached = responseData.data.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.doctorDao().clearAll()
                    database.remoteKeysDao().clearRemoteKeys()
                }
                val prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.data.map {
                    RemoteKeys(
                        id = it.dokterId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                database.remoteKeysDao().insertAll(keys)
                database.doctorDao().insertDoctor(responseData.data)
            }
            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DataDoctor>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.dokterId?.let { repoId ->
                database.remoteKeysDao().getRemoteKeysId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DataDoctor>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.dokterId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DataDoctor>): RemoteKeys? {
        return state.pages.firstOrNull() { it.data.isNotEmpty()}?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.dokterId)
        }
    }


    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}