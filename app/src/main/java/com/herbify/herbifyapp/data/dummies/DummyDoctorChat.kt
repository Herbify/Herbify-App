package com.herbify.herbifyapp.data.dummies

import com.herbify.herbifyapp.model.DoctorChat

object DummyDoctorChat {
    private val chat1 = DoctorChat(0, null, "Hello, how can I help you?", "2023-06-01")
    private val chat2 = DoctorChat(1, 12345, "Good morning! How are you feeling today?", "2023-06-02")
    private val chat3 = DoctorChat(0, null, "I'm sorry to hear that. Can you describe your symptoms?", "2023-06-03")
    private val chat4 = DoctorChat(1, 12345, "Based on your description, it could be a common cold.", "2023-06-04")
    private val chat5 = DoctorChat(0, null, "Thank you, doctor. I'll take your advice.", "2023-06-05")

    val dummyData = listOf(chat1, chat2, chat3, chat4, chat5)
}