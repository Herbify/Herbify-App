package com.herbify.herbifyapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.herbify.herbifyapp.model.Herbal

@Database(
    entities = [Herbal::class],
    version = 1,
    exportSchema = false
)

abstract class HerbalDatabase: RoomDatabase() {
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