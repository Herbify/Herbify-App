package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.herbify.herbifyapp.data.local.dao.BrewDao
import com.herbify.herbifyapp.data.local.dao.HerbalDao
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.Brewed
import com.herbify.herbifyapp.utils.AppExecutors

class BrewRepository(private val brewDao: BrewDao, private val executors: AppExecutors) {
    fun getAllBrewed(): LiveData<List<Brewed>> {
        return brewDao.getAllBrewed()
    }
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
    fun deleteBrewed(id: Int) {
      executors.diskIO.execute {
          brewDao.delete(id)
      }
    }
}