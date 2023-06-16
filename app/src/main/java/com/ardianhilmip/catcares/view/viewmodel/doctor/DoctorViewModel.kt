package com.ardianhilmip.catcares.view.viewmodel.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.data.repository.DoctorRepository

class DoctorViewModel(doctorRepository: DoctorRepository) : ViewModel(){
    val doctor: LiveData<PagingData<DataDoctor>> = doctorRepository.getDoctor().cachedIn(viewModelScope)
}