package com.herbify.herbifyapp.repository

import com.herbify.herbifyapp.data.local.dao.BrewDao
import com.herbify.herbifyapp.model.Brewed
import com.herbify.herbifyapp.utils.AppExecutors

class BrewRepository(private val brewDao: BrewDao, private val executors: AppExecutors) {
    fun getAllBrewed()= brewDao.getAllBrewedAsHerbal()
    fun addBrewed(brewed: Brewed) {
        executors.diskIO.execute {
            brewDao.insert(brewed)
        }
    }
    fun reset(){
        executors.diskIO.execute{
            brewDao.deleteAll()
        }
    }
}