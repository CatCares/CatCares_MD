package com.ardianhilmip.catcares.data.remote.response.article

import com.ardianhilmip.catcares.data.local.entity.DataItem
import com.google.gson.annotations.SerializedName

data class ArtikelResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)