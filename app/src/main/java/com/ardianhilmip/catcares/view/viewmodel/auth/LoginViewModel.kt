package com.ardianhilmip.catcares.view.viewmodel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.data.remote.response.auth.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    // Login Model
    private val _loginData = MutableLiveData<User>()
    val loginData: LiveData<User> = _loginData

    // Error
    private val _error = MutableLiveData(true)
    val error: LiveData<Boolean> = _error

    // Message
    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> = _status

    // Loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _error.value = false
                        _status.value = it.status
                        _loginData.value = it.data
                    }
                } else {
                    _error.value = true
                    _status.value = response.code()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

    }

    companion object {
        private val TAG = LoginViewModel::class.java.simpleName
    }
}