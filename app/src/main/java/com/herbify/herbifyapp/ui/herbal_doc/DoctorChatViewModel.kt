package com.herbify.herbifyapp.ui.herbal_doc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.response.chat.AllChatResponse
import com.herbify.herbifyapp.data.remote.response.chat.Conversation
import com.herbify.herbifyapp.data.remote.response.chat.ConversationData
import com.herbify.herbifyapp.data.remote.response.chat.MessagesItem
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.repository.ChatRepository
import com.herbify.herbifyapp.repository.DoctorRepository
import com.herbify.herbifyapp.utils.RepositoryResult
import retrofit2.Call
import retrofit2.Response

class DoctorChatViewModel(private val doctorRepository: DoctorRepository, private val chatRepository: ChatRepository, private val preferences: UserPreferences): ViewModel() {
    private var _currentChat = MutableLiveData<List<ConversationData>?>()
    val currentChat : MutableLiveData<List<ConversationData>?> = _currentChat

    fun createConversation(idUser: Int, idDoctor: Int): LiveData<RepositoryResult<Conversation>> {
        val raw = JsonObject()
        raw.addProperty("idUser", idUser)
        raw.addProperty("idDoctor", idDoctor)
        return chatRepository.createConversation(raw)
    }
    fun getDoctor(id: Int) = doctorRepository.getDoctorById(id)
    fun getChat() {
        val apiService = ApiConfig().getApiService()
        val client = apiService.getConversation(preferences.getIdUser())
        client.enqueue(object : retrofit2.Callback<AllChatResponse>{
            override fun onResponse(
                call: Call<AllChatResponse>,
                response: Response<AllChatResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!
                    if(responseBody.data != null){
                        _currentChat.value = responseBody.data
                    }
                }
            }

            override fun onFailure(call: Call<AllChatResponse>, t: Throwable) {
            }
        })
    }

    fun sendChat(message: String, conversation: Conversation): LiveData<RepositoryResult<MessagesItem>> {
        val raw = JsonObject()
        raw.addProperty("messageGroupId", conversation.id)
        raw.addProperty("sender", conversation.userId)
        raw.addProperty("recipient", conversation.doctorId)
        raw.addProperty("fromUser", true)
        raw.addProperty("content", message)
        return chatRepository.sendMessage(raw)
    }
    fun finishConversation(conversation: Conversation) = chatRepository.finishConversation(conversation)
}