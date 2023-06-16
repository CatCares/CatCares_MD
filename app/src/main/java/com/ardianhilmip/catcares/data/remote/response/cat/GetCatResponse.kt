package com.ardianhilmip.catcares.data.remote.response.cat

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.ardianhilmip.catcares.data.local.entity.DataCat
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCatResponse(

	@field:SerializedName("data")
	val data: List<DataCat>,

	@field:SerializedName("message")
	val message: String
) : Parcelable


