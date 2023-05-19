package com.herbify.herbifyapp.data

import android.content.Context
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context){
        val apiService = ApiConfig().getApiService()
        val database = HerbalDatabase.getDatabase(context)

    }
}