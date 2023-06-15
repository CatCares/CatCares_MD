package com.ardianhilmip.catcares.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardianhilmip.catcares.data.remote.response.article.ArticleResponseItem

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<ArticleResponseItem>)

    @Query("SELECT * FROM tbl_article")
    fun getAllArticle(): PagingSource<Int, ArticleResponseItem>

    @Query("DELETE FROM tbl_article")
    suspend fun clearAll()
}