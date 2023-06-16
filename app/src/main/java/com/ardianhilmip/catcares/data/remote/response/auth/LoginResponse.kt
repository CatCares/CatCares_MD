package com.ardianhilmip.catcares.data.remote.response.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

    @field:SerializedName("data")
    val data: User,

    @field:SerializedName("status")
    val status: Int
) : Parcelable

@Parcelize
data class User (
    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("noHP")
    val noHP: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
) : Parcelable
