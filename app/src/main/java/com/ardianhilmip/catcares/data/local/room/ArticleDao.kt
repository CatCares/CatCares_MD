package com.ardianhilmip.catcares.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.data.local.entity.DataItem

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(doctor: List<DataItem>)

    @Query("SELECT * FROM tbl_article")
    fun getAllArticle(): PagingSource<Int, DataItem>

    @Query("DELETE FROM tbl_doctor")
    suspend fun clearAll()
}