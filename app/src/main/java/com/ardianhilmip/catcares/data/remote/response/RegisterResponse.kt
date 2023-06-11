package com.ardianhilmip.catcares.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: DataRegister,

	@field:SerializedName("status")
	val status: Int
)

data class DataRegister(

	@field:SerializedName("_id")
	val id: String? = null
)
