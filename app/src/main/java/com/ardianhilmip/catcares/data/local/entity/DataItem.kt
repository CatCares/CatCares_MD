package com.ardianhilmip.catcares.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "tbl_article")
data class DataItem(
	@PrimaryKey
	@field:SerializedName("artikelId")
	val artikelId: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("konten")
	val konten: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("judul")
	val judul: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)