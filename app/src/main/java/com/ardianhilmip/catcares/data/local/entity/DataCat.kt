package com.ardianhilmip.catcares.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tbl_cat")
data class DataCat(

    @field:SerializedName("kelamin")
    val kelamin: String,

    @field:SerializedName("warna")
    val warna: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("umur")
    val umur: Int,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("foto")
    val foto: String,

    @PrimaryKey
    @field:SerializedName("kucingId")
    val kucingId: String,

    @field:SerializedName("ras")
    val ras: String,

    @field:SerializedName("berat")
    val berat: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: String
) : Parcelable

