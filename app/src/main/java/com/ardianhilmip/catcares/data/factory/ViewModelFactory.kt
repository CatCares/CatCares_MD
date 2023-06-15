package com.ardianhilmip.catcares.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardianhilmip.catcares.di.Injection
import com.ardianhilmip.catcares.view.viewmodel.doctor.DoctorViewModel

class ViewModelFactory (private val context: Context, val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoctorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DoctorViewModel(Injection.doctorRepository(context, token)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}