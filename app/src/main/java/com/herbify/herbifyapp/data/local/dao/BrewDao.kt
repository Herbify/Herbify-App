package com.herbify.herbifyapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.Brewed

@Dao
interface BrewDao {
    @Query("SELECT * FROM brewed")
    fun getAllBrewed(): LiveData<List<Brewed>>

    @Query(
        "SELECT herbals.image, herbals.createdAt, herbals.scientificName, herbals.name, herbals.description, herbals.id, herbals.benefit, herbals.updatedAt FROM brewed LEFT JOIN herbals ON brewed.herbal_id = herbals.id"
    )
    fun getAllBrewedAsHerbal(): LiveData<List<HerbalData>>

    @Delete
    fun delete(brewed: Brewed)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(brewed: Brewed)

    @Query("DELETE FROM brewed")
    fun deleteAll()
}