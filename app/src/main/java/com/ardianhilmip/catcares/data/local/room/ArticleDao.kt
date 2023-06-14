package com.ardianhilmip.catcares.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardianhilmip.catcares.data.local.Article.Article
import com.ardianhilmip.catcares.data.local.entity.ArticleDataItem
import com.ardianhilmip.catcares.data.local.entity.DataDoctor

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<ArticleDataItem>)

    @Query("SELECT * FROM tbl_article")
    fun getAllArticle(): PagingSource<Int, ArticleDataItem>

    @Query("DELETE FROM tbl_article")
    suspend fun clearAll()
}