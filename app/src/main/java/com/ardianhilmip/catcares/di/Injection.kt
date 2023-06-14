package com.ardianhilmip.catcares.di

import android.content.Context
import com.ardianhilmip.catcares.data.local.room.CatCaresDB
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.repository.ArticleRepository
import com.ardianhilmip.catcares.data.repository.DoctorRepository

object Injection {
    fun doctorRepository(context: Context, token: String): DoctorRepository {
        val database = CatCaresDB.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return DoctorRepository(database, apiService, token)
    }
    fun articleRepository(context: Context, token: String): ArticleRepository {
        val database = CatCaresDB.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return ArticleRepository(database, apiService, token)
    }
}