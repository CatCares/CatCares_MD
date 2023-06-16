package com.ardianhilmip.catcares.data.remote.response.prediction

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("isRingworm")
	val isRingworm: Boolean
)

