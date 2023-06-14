package com.ardianhilmip.catcares.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardianhilmip.catcares.data.local.entity.ArticleDataItem
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.data.local.entity.RemoteKeys

@Database(
    entities = [DataDoctor::class, ArticleDataItem::class, RemoteKeys::class],
    version = 4,
    exportSchema = false
)
abstract class CatCaresDB: RoomDatabase() {

    abstract fun doctorDao(): DoctorDao
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        var INSTANCE : CatCaresDB? = null

        @JvmStatic
        fun getDatabase(context: Context): CatCaresDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    CatCaresDB::class.java, "catcares.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}