package com.herbify.herbifyapp.data

import android.content.Context
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.repository.HerbalRepository

object Injection {
    fun provideHerbalRepository(context: Context): HerbalRepository{
        val database = HerbalDatabase.getDatabase(context)
        val apiService = ApiConfig().getApiService()
        return HerbalRepository(database, apiService)
    }
}