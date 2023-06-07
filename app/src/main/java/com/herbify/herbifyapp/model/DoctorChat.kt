package com.herbify.herbifyapp.model

data class DoctorChat(
    val type: Int,
    val doctorId: Int?,
    val content: String,
    val date: String,
)