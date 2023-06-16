package com.ardianhilmip.catcares.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ardianhilmip.catcares.data.local.entity.DataCat
import com.ardianhilmip.catcares.data.local.room.CatCaresDB
import com.ardianhilmip.catcares.data.remote.api.ApiService
import com.ardianhilmip.catcares.data.remote.mediator.CatRemoteMediator

class CatRepository(
    private val database: CatCaresDB,
    private val service: ApiService,
    private val token: String
) {
    fun getCat(): LiveData<PagingData<DataCat>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = CatRemoteMediator(database, service, token),
            pagingSourceFactory = {
                database.catDao().getAllCat()
            }
        ).liveData
    }
}