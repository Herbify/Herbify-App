package com.herbify.herbifyapp

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.herbify.herbifyapp.data.dummies.DummyHerbalResponse
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.RemoteKey

@OptIn(ExperimentalPagingApi::class)
class HerbalRemoteMediator(
    private val database: HerbalDatabase,
    private val apiService: ApiService): RemoteMediator<Int, HerbalData>() {
    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HerbalData>
    ): MediatorResult {

        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return  MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.getAllHerbals(state.config.pageSize, page).data
            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    database.getRemoteKeys().deleteRemoteKeys()
                    database.getHerbalDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1
                val keys = responseData.map{herbalData ->
                    RemoteKey(id = herbalData.id, prevKey = prevKey,nextKey = nextKey)
                }
                database.getRemoteKeys().insertAll(keys)
                database.getHerbalDao().insertHerbal(responseData)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (exception: java.lang.Exception){
            Log.e("HerbalMediator", exception.message.toString())
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, HerbalData>): RemoteKey?{
        return state.pages.lastOrNull{it.data.isNotEmpty()}?.data?.lastOrNull()?.let { data ->
            database.getRemoteKeys().getRemoteKeysId(data.id)
        }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, HerbalData>): RemoteKey?{
        return state.pages.firstOrNull{it.data.isNotEmpty()}?.data?.firstOrNull()?.let { data ->
            database.getRemoteKeys().getRemoteKeysId(data.id)
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, HerbalData>): RemoteKey?{
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getRemoteKeys().getRemoteKeysId(id)
            }
        }
    }
}