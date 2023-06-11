package com.ardianhilmip.catcares.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    // Error
    private val _error = MutableLiveData(true)
    val error: LiveData<Boolean> = _error

    // Message
    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> = _status

    // Loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .register(firstName, lastName, email, password, confirmPassword)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _error.value = false
                        _status.value = it.status
                    }
                } else {
                    _error.value = true
                    _status.value = response.code()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }

    companion object {
        private val TAG = RegisterViewModel::class.java.simpleName
    }
}