package com.ardianhilmip.catcares.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardianhilmip.catcares.data.local.entity.DataDoctor

@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: List<DataDoctor>)

    @Query("SELECT * FROM tbl_doctor")
    fun getAllDoctor(): PagingSource<Int, DataDoctor>

    @Query("DELETE FROM tbl_doctor")
    suspend fun clearAll()
}