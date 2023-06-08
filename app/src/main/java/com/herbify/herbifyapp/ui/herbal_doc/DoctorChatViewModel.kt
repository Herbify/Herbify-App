package com.herbify.herbifyapp.ui.herbal_doc

import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.model.DoctorChat
import com.herbify.herbifyapp.repository.ChatRepository
import com.herbify.herbifyapp.repository.DoctorRepository

class DoctorChatViewModel(private val doctorRepository: DoctorRepository, private val chatRepository: ChatRepository): ViewModel() {
    fun getDoctor(id: Int) = doctorRepository.getDoctorById(id)
    fun getChat(doctorId: Int) = chatRepository.getChatByDoctor(doctorId)
}