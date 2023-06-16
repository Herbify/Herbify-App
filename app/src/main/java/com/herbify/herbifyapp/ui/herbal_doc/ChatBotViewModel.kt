package com.herbify.herbifyapp.ui.herbal_doc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.chat.ChatbotResponse
import retrofit2.Call
import retrofit2.Response

class ChatBotViewModel(): ViewModel() {
    fun sendChat(content: String):LiveData<String>{
        val apiService = ApiConfig().getApiService()
        val result = MediatorLiveData<String>()
        val raw = JsonObject()
        raw.addProperty("content", content)
        val client = apiService.sendChatBot(raw)
        client.enqueue(object : retrofit2.Callback<ChatbotResponse>{
            override fun onResponse(
                call: Call<ChatbotResponse>,
                response: Response<ChatbotResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()!!.data
                    result.value = body?.answer!!
                }
            }

            override fun onFailure(call: Call<ChatbotResponse>, t: Throwable) {
            }

        })
        return result
    }
}

data class ChatBotModel(
    val fromUser : Boolean,
    val content: String
)