package com.ardianhilmip.catcares.data.remote.response.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
	@field:SerializedName("data")
	val data: DataProfile
)

data class DataProfile(

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
)
