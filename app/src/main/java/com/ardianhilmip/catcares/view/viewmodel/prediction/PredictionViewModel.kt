package com.ardianhilmip.catcares.view.viewmodel.prediction

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.prediction.PredictionResponse
import com.ardianhilmip.catcares.view.viewmodel.profile.UpdateViewModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictionViewModel: ViewModel() {
    // Error
    private val _error = MutableLiveData(true)
    val error: LiveData<Boolean> = _error

    // Loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //isRingworm
    private val _isRingworm = MutableLiveData<Boolean>()
    val isRingworm: LiveData<Boolean> = _isRingworm

    fun prediction(token:String, image: MultipartBody.Part) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().prediction("Bearer $token", image)
        client.enqueue(object: Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _error.value = false
                        _isRingworm.value = it.isRingworm
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private val TAG = PredictionViewModel::class.java.simpleName
    }
}