package com.ardianhilmip.catcares.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tbl_article")
data class ArticleDataItem(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("konten")
    val konten: String,

    @field:SerializedName("__v")
    val v: Int,

    @field:SerializedName("link")
    val link: String,

    @PrimaryKey
    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("judul")
    val judul: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
): Parcelable
