package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.*
import com.herbify.herbifyapp.HerbalRemoteMediator
import com.herbify.herbifyapp.data.local.dao.DoctorDao
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.DoctorData
import com.herbify.herbifyapp.data.remote.response.DoctorResponse
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.Doctor
import com.herbify.herbifyapp.utils.AppExecutors
import com.herbify.herbifyapp.utils.RepositoryResult
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DoctorRepository(private val doctorDao: DoctorDao, private val apiService: ApiService, private val appExecutors: AppExecutors) {
    private val result = MediatorLiveData<RepositoryResult<List<Doctor>>>()

    fun getAllDoctors(): LiveData<RepositoryResult<List<Doctor>>>{
        result.value = RepositoryResult.Loading
        val client = apiService.getAllDoctors()
        client.enqueue(object : retrofit2.Callback<DoctorResponse> {
            override fun onResponse(
                call: Call<DoctorResponse>,
                response: Response<DoctorResponse>
            ) {
                if(response.isSuccessful){
                    val doctors = response.body()?.data
                    val doctorList = ArrayList<Doctor>()
                    appExecutors.diskIO.execute{
                        doctors?.forEach { doctor ->
                            val item = Doctor(
                                doctor.createdAt,
                                doctor.name,
                                doctor.verifiedAt,
                                doctor.photo,
                                doctor.id,
                                doctor.email,
                                doctor.status,
                                doctor.updatedAt
                            )
                            doctorList.add(item)
                        }
                        doctorDao.deleteAll()
                        doctorDao.insertDoctors(doctorList)
                    }
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }
        })
        val localData = doctorDao.getAllDoctors()
        result.addSource(localData){doctorData ->
            result.value = RepositoryResult.Success(doctorData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: DoctorRepository? = null
        fun getInstance(
            doctorDao: DoctorDao,
            apiService: ApiService,
            appExecutors: AppExecutors
        ): DoctorRepository =
            instance ?: synchronized(this) {
                instance ?: DoctorRepository(doctorDao, apiService, appExecutors)
            }.also { instance = it }
    }
}