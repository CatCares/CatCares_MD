package com.ardianhilmip.catcares.data.remote.response.doctor

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorResponse(

    @field:SerializedName("data")
    val data: List<DataDoctor>,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable


