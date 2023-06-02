package com.herbify.herbifyapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData

@Dao
interface HerbalDao {
    @Query("SELECT * FROM herbals ORDER BY name DESC")
    fun getAllHerbals() : PagingSource<Int, HerbalData>

    @Query("SELECT * FROM herbals ORDER BY name DESC")
    suspend fun deleteAll() : PagingSource<Int, HerbalData>

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertHerbal(herbalData: List<HerbalData>)
}
