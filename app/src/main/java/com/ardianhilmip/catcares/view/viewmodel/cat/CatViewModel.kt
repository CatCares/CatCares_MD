package com.ardianhilmip.catcares.view.viewmodel.cat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.cat.CatResponse
import com.ardianhilmip.catcares.view.viewmodel.profile.UpdateViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatViewModel : ViewModel(){



    // Message
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    // Loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addCat(token: String, nama: RequestBody?, ras: RequestBody?, kelamin: RequestBody?, umur: RequestBody?, berat: RequestBody?, warna: RequestBody?, foto: MultipartBody.Part?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addCat("Bearer $token", nama, ras, kelamin, umur, berat, warna, foto)
        client.enqueue(object: Callback<CatResponse> {
            override fun onResponse(
                call: Call<CatResponse>,
                response: Response<CatResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _message.value = it.message
                    }
                } else {
                    _message.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CatResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private val TAG = CatViewModel::class.java.simpleName
    }
}