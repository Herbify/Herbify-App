package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.*
import com.herbify.herbifyapp.HerbalRemoteMediator
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalIdResponse
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalResponse
import com.herbify.herbifyapp.utils.RepositoryResult
import retrofit2.Call
import retrofit2.Response

class HerbalRepository(private val database: HerbalDatabase, private val apiService: ApiService) {
    fun getHerbals():LiveData<PagingData<HerbalData>>{
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            remoteMediator = HerbalRemoteMediator(database, apiService),
            pagingSourceFactory ={
                database.getHerbalDao().getAllHerbals()
            }
        ).liveData
    }

    fun getHerbals(id: Int) : LiveData<HerbalData>{
        val client = apiService.herbalById(id)
        val result = MediatorLiveData<HerbalData>()
        client.enqueue(object : retrofit2.Callback<HerbalIdResponse>{
            override fun onResponse(
                call: Call<HerbalIdResponse>,
                response: Response<HerbalIdResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()?.data!!
                    val herbalData = HerbalData(
                        body.image!!,
                        body.createdAt,
                        body.scientificName!!,
                        body.name!!,
                        body.description!!,
                        body.id!!,
                        body.benefit.toString(),
                        body.updatedAt
                    )
                    result.value = herbalData
                }
            }

            override fun onFailure(call: Call<HerbalIdResponse>, t: Throwable) {
            }

        })
        return result
    }

    fun searchHerbal(key: String):LiveData<PagingData<HerbalData>>{
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            remoteMediator = HerbalRemoteMediator(database, apiService),
            pagingSourceFactory ={
                database.getHerbalDao().searchHerbal(key)
            }
        ).liveData
    }

    fun getHerbal(name: String):LiveData<RepositoryResult<HerbalData>>{
        val result = MediatorLiveData<RepositoryResult<HerbalData>>()
        val client = apiService.searchHerbalClient(name)
        client.enqueue(object: retrofit2.Callback<HerbalResponse>{
            override fun onResponse(
                call: Call<HerbalResponse>,
                response: Response<HerbalResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    if(body.data.isNotEmpty()){
                        result.value = RepositoryResult.Success(body.data[0])
                    }else{
                        result.value = RepositoryResult.Error("Herbal not found")
                    }
                }else{
                    result.value = RepositoryResult.Error("Cannot get herbal")
                }
            }

            override fun onFailure(call: Call<HerbalResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }

        })
        return result
    }
}