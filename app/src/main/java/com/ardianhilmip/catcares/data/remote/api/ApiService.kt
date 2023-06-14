package com.ardianhilmip.catcares.data.remote.api

import com.ardianhilmip.catcares.data.remote.response.article.ArticleResponse
import com.ardianhilmip.catcares.data.remote.response.auth.RegisterResponse
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.data.remote.response.doctor.DoctorResponse
import com.ardianhilmip.catcares.data.remote.response.profile.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    //Update user
    @Multipart
    @PUT("user/update-profile")
    fun updateUser(
        @Header("Authorization") token_auth: String,
        @Part("firstName") firstName: RequestBody?,
        @Part("lastName") lastName: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part("noHP") noHP: RequestBody?,
        @Part foto: MultipartBody.Part?
    ): Call<ProfileResponse>

    //Get Doctor
    @GET("dokter/list-dokter")
    suspend fun getListDoctor(
        @Header("Authorization") token_auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): DoctorResponse

    //Get Article
    @GET("/artikel")
    suspend fun getListArticle(
        @Header("Authorization") token_auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ArticleResponse

}