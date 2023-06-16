package com.ardianhilmip.catcares.view.viewmodel.cat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ardianhilmip.catcares.data.local.entity.DataCat
import com.ardianhilmip.catcares.data.repository.CatRepository

class GetCatViewModel(catRepository: CatRepository): ViewModel() {
    val cat: LiveData<PagingData<DataCat>> = catRepository.getCat().cachedIn(viewModelScope)
}