package com.herbify.herbifyapp.ui.herbal_doc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.model.Doctor
import com.herbify.herbifyapp.repository.DoctorRepository

class DoctorViewModel(private val doctorRepository: DoctorRepository): ViewModel() {

    val doctor = doctorRepository.getAllDoctors()

    fun getExistedDoctor(){
        doctor
    }
}