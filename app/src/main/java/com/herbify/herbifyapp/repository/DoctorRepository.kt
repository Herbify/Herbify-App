package com.herbify.herbifyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.*
import com.herbify.herbifyapp.data.local.dao.DoctorDao
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.DoctorIdResponse
import com.herbify.herbifyapp.data.remote.response.DoctorResponse
import com.herbify.herbifyapp.model.Doctor
import com.herbify.herbifyapp.utils.AppExecutors
import com.herbify.herbifyapp.utils.RepositoryResult
import retrofit2.Call
import retrofit2.Response

class DoctorRepository(private val doctorDao: DoctorDao, private val apiService: ApiService, private val appExecutors: AppExecutors) {
    private val resultList = MediatorLiveData<RepositoryResult<List<Doctor>>>()
    private val resultDoctor = MediatorLiveData<RepositoryResult<Doctor>>()

    fun getAllDoctors(): LiveData<RepositoryResult<List<Doctor>>>{
        resultList.value = RepositoryResult.Loading
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
                        Log.d("Doctor Repository", doctorList.toString())
                        doctorDao.deleteAll()
                        doctorDao.insertDoctors(doctorList)
                    }
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                resultList.value = RepositoryResult.Error(t.message.toString())
            }
        })
        val localData = doctorDao.getAllDoctors()
        resultList.addSource(localData){ doctorData: List<Doctor> ->
            resultList.value = RepositoryResult.Success(doctorData)
        }

        return resultList
    }

    fun getDoctorById(id: Int): LiveData<RepositoryResult<Doctor>>{
        resultDoctor.value = RepositoryResult.Loading
        val client = apiService.getDoctor(id)
        client.enqueue(object : retrofit2.Callback<DoctorIdResponse> {
            override fun onResponse(
                call: Call<DoctorIdResponse>,
                response: Response<DoctorIdResponse>
            ) {
                if(response.isSuccessful){
                    val doctor = response.body()?.data
                    appExecutors.diskIO.execute{
                        if(doctor != null){
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
                            resultDoctor.value = RepositoryResult.Success(item)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DoctorIdResponse>, t: Throwable) {
                resultList.value = RepositoryResult.Error(t.message.toString())
            }
        })
        return resultDoctor
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