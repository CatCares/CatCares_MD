package com.ardianhilmip.catcares.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

    @field:SerializedName("data")
    val data: DataLogin,

    @field:SerializedName("status")
    val status: Int
) : Parcelable

@Parcelize
data class DataLogin(

    @field:SerializedName("token")
    val token: String? = null
) : Parcelable
