package com.ardianhilmip.catcares.data.remote.response.article

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("konten")
	val konten: String,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("judul")
	val judul: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

