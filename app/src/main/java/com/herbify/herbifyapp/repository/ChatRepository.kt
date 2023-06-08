package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.model.DoctorChat
import com.herbify.herbifyapp.utils.RepositoryResult

class ChatRepository(private val database: HerbalDatabase, private val apiService: ApiService) {
    var doctorChats = MediatorLiveData<RepositoryResult<List<DoctorChat>>>()
    fun getChatByDoctor(doctorId: Int): LiveData<RepositoryResult<List<DoctorChat>>>{
        return doctorChats
    }
}