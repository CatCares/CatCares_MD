package com.ardianhilmip.catcares.view.viewmodel.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardianhilmip.catcares.data.remote.api.ApiConfig
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.data.remote.response.auth.User
import com.ardianhilmip.catcares.data.remote.response.profile.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateViewModel: ViewModel() {

    // Error
    private val _error = MutableLiveData(true)
    val error: LiveData<Boolean> = _error

    // Message
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    // Loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun update(token: String, firstName: RequestBody?, lastName: RequestBody?, address: RequestBody?, noHp: RequestBody?, foto: MultipartBody.Part) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().updateUser("Bearer $token", firstName, lastName, address, noHp, foto)
        client.enqueue(object: Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _error.value = false
                        _message.value = it.message
                    }
                } else {
                    _error.value = true
                    _message.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private val TAG = UpdateViewModel::class.java.simpleName
    }
}