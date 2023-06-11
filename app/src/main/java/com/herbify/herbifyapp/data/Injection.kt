package com.herbify.herbifyapp.data

import android.content.Context
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.repository.ArticleRepository
import com.herbify.herbifyapp.repository.BrewRepository
import com.herbify.herbifyapp.repository.DoctorRepository
import com.herbify.herbifyapp.repository.HerbalRepository
import com.herbify.herbifyapp.utils.AppExecutors

object Injection {
    fun provideHerbalRepository(context: Context): HerbalRepository{
        val database = HerbalDatabase.getDatabase(context)
        val apiService = ApiConfig().getApiService()
        return HerbalRepository(database, apiService)
    }
    fun provideDoctorRepository(context: Context): DoctorRepository{
        val doctorDao = HerbalDatabase.getDatabase(context).getDoctorDao()
        val apiService = ApiConfig().getApiService()
        val appExecutors = AppExecutors()
        return DoctorRepository.getInstance(doctorDao, apiService, appExecutors)
    }

    fun provideArticleRepository(context: Context): ArticleRepository{
        val apiService = ApiConfig().getApiService()
        val userPreferences = UserPreferences.getInstance(context)
        return ArticleRepository(apiService,userPreferences)
    fun provideBrewRepository(context: Context): BrewRepository{
        return BrewRepository(HerbalDatabase.getDatabase(context).getBrewedDao(),AppExecutors())
    }
}