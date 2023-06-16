package com.ardianhilmip.catcares.data.remote.api

import com.ardianhilmip.catcares.data.remote.response.article.ArtikelResponse
import com.ardianhilmip.catcares.data.remote.response.auth.RegisterResponse
import com.ardianhilmip.catcares.data.remote.response.auth.LoginResponse
import com.ardianhilmip.catcares.data.remote.response.cat.CatResponse
import com.ardianhilmip.catcares.data.remote.response.cat.GetCatResponse
import com.ardianhilmip.catcares.data.remote.response.doctor.DoctorResponse
import com.ardianhilmip.catcares.data.remote.response.prediction.PredictionResponse
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

    //Add Cat
    @Multipart
    @POST("kucing/add-kucing")
    fun addCat(
        @Header("Authorization") token_auth: String,
        @Part("nama") nama: RequestBody?,
        @Part("ras") ras: RequestBody?,
        @Part("kelamin") kelamin: RequestBody?,
        @Part("umur") umur: RequestBody?,
        @Part("berat") berat: RequestBody?,
        @Part("warna") warna: RequestBody?,
        @Part foto: MultipartBody.Part?
    ): Call<CatResponse>

    //GetCat
    @GET("kucing/user")
    suspend fun getCat(
        @Header("Authorization") token_auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): GetCatResponse

    //Prediction
    @Multipart
    @POST("prediction/ringworm")
    fun prediction(
        @Header("Authorization") token_auth: String,
        @Part image: MultipartBody.Part?
    ): Call<PredictionResponse>

    //Get Doctor
    @GET("dokter/list-dokter")
    suspend fun getListDoctor(
        @Header("Authorization") token_auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): DoctorResponse

    //Get Article
    @GET("artikel/list-artikel")
    suspend fun getArticle(
        @Header("Authorization") token_auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ArtikelResponse
}