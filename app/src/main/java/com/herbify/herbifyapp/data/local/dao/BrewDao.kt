package com.herbify.herbifyapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.Brewed

@Dao
interface BrewDao {
    @Query("SELECT * FROM brewed")
    fun getAllBrewed(): LiveData<List<Brewed>>

    @Delete
    fun delete(brewed: Brewed)

    @Query("DELETE FROM brewed WHERE herbal_id = :id")
    fun delete(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(brewed: Brewed)

    @Query("DELETE FROM brewed")
    fun deleteAll()
}