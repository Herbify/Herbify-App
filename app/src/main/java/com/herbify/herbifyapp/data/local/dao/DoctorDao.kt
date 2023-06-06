package com.herbify.herbifyapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.herbify.herbifyapp.model.Doctor

@Dao
interface DoctorDao {
    @Query("SELECT * FROM doctors")
    fun getAllDoctors(): LiveData<List<Doctor>>

    @Query("SELECT * FROM doctors WHERE id = :id")
    fun getDoctor(id: Long): Doctor

    @Query("DELETE FROM doctors")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertDoctors(doctorList: ArrayList<Doctor>)
}