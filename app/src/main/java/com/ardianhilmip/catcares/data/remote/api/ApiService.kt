package com.ardianhilmip.catcares.data.remote.api

import com.ardianhilmip.catcares.data.remote.response.LoginResponse
import com.ardianhilmip.catcares.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    //Register
    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPasword: String,
    ): Call<RegisterResponse>

    //Login
    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}