package com.ardianhilmip.catcares.data.remote.response.article

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleResponse(
	@field:SerializedName("ArticleResponse")
	val articleResponse: List<ArticleResponseItem>
) :Parcelable

@Parcelize
@Entity(tableName = "tbl_article")
data class ArticleResponseItem(

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

	@PrimaryKey
	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("judul")
	val judul: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
):Parcelable
