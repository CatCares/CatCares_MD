package com.ardianhilmip.catcares.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.data.local.room.CatCaresDB
import com.ardianhilmip.catcares.data.remote.api.ApiService
import com.ardianhilmip.catcares.data.remote.mediator.DoctorRemoteMediator

class DoctorRepository(
    private val database: CatCaresDB,
    private val service: ApiService,
    private val token: String
) {
    fun getDoctor(): LiveData<PagingData<DataDoctor>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = DoctorRemoteMediator(database, service, token),
            pagingSourceFactory = {
                database.doctorDao().getAllDoctor()
            }
        ).liveData
    }
}