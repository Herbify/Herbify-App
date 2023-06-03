package com.herbify.herbifyapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.herbify.herbifyapp.data.local.dao.HerbalDao
import com.herbify.herbifyapp.data.local.dao.RemoteKeysDao
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.RemoteKey

@Database(
    entities = [HerbalData::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)

abstract class HerbalDatabase: RoomDatabase() {
    abstract fun getHerbalDao(): HerbalDao
    abstract fun getRemoteKeys(): RemoteKeysDao
    companion object{
        @Volatile
        private var INSTANCE : HerbalDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): HerbalDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HerbalDatabase::class.java, "herbal_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}