package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.herbify.herbifyapp.HerbalRemoteMediator
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData

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
}