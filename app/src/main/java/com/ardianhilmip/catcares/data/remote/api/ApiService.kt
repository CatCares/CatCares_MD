package com.ardianhilmip.catcares.data.remote.api

import android.media.session.MediaSession.Token
import com.ardianhilmip.catcares.data.remote.response.auth.RegisterResponse
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
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

    //User
    @GET("user/profile")
    fun getUser(
        @Header("Authorization") token_auth: String
    ): Call<LoginResponse>

    //
}