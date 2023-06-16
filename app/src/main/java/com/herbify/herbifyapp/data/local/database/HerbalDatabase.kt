package com.herbify.herbifyapp.data.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.herbify.herbifyapp.data.local.dao.BrewDao
import com.herbify.herbifyapp.data.local.dao.DoctorDao
import com.herbify.herbifyapp.data.local.dao.HerbalDao
import com.herbify.herbifyapp.data.local.dao.RemoteKeysDao
import com.herbify.herbifyapp.data.remote.response.DoctorData
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.Brewed
import com.herbify.herbifyapp.model.Doctor
import com.herbify.herbifyapp.model.RemoteKey

@Database(
    entities = [HerbalData::class, RemoteKey::class, Doctor::class, Brewed::class],
    version = 11,
    exportSchema = false
)

abstract class HerbalDatabase: RoomDatabase() {
    abstract fun getHerbalDao(): HerbalDao
    abstract fun getRemoteKeys(): RemoteKeysDao
    abstract fun getDoctorDao(): DoctorDao
    abstract fun getBrewedDao(): BrewDao
    companion object{
        @Volatile
        private var INSTANCE : HerbalDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): HerbalDatabase {
            Log.d("HerbalDatabase","Database get")
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