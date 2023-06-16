package com.ardianhilmip.catcares.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardianhilmip.catcares.data.local.entity.DataCat

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(cat: List<DataCat>)

    @Query("SELECT * FROM tbl_cat")
    fun getAllCat(): PagingSource<Int, DataCat>

    @Query("DELETE FROM tbl_cat")
    suspend fun clearAll()
}