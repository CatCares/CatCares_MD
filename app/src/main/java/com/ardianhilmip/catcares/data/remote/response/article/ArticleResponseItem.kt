package com.ardianhilmip.catcares.data.remote.response.article

import com.google.gson.annotations.SerializedName

data class ArticleResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("konten")
	val konten: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)