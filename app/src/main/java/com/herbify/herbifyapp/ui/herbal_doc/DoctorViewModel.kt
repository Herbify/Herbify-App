package com.herbify.herbifyapp.ui.herbal_doc

import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.repository.DoctorRepository

class DoctorViewModel(private val doctorRepository: DoctorRepository): ViewModel() {
    fun getAllDoctor() = doctorRepository.getAllDoctors()
}