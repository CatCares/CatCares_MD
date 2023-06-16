package com.ardianhilmip.catcares.data.remote.response.cat

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("kucingId")
	val kucingId: String? = null
) : Parcelable
