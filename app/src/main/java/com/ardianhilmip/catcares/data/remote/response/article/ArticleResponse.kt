package com.ardianhilmip.catcares.data.remote.response.article

import com.ardianhilmip.catcares.data.local.entity.ArticleDataItem
import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("ArticleResponse")
	val articleResponse: List<ArticleDataItem>
)