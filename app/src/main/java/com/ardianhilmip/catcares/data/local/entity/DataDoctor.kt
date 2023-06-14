package com.ardianhilmip.catcares.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tbl_doctor")
data class DataDoctor(
    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("foto")
    val foto: String,

    @field:SerializedName("telepon")
    val telepon: String,

    @field:SerializedName("tipe")
    val tipe: String,

    @PrimaryKey
    @field:SerializedName("dokterId")
    val dokterId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String

) : Parcelable

