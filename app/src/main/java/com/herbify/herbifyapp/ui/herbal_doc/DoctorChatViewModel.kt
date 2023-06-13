package com.herbify.herbifyapp.ui.herbal_doc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.response.chat.*
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.repository.ChatRepository
import com.herbify.herbifyapp.repository.DoctorRepository
import com.herbify.herbifyapp.utils.RepositoryResult
import retrofit2.Call
import retrofit2.Response

class DoctorChatViewModel(private val doctorRepository: DoctorRepository, private val chatRepository: ChatRepository, private val preferences: UserPreferences): ViewModel() {
    private var _currentChat = MutableLiveData<ConversationData?>()
    val currentChat : MutableLiveData<ConversationData?> = _currentChat

    fun createConversation(idDoctor: Int): LiveData<RepositoryResult<SimpleConversationResponse>> {
        val raw = JsonObject()
        raw.addProperty("idUser", preferences.getIdUser())
        raw.addProperty("idDoctor", idDoctor)
        return chatRepository.createConversation(raw)
    }
    fun getConversationByUserId(userId: Int) = chatRepository.getConversationRoom(userId)
    fun getDoctor(id: Int) = doctorRepository.getDoctorById(id)
    fun getChat(conversation: Conversation) {
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
                        responseBody.data.forEach { conversationData ->
                            if(conversationData.id == conversation.id){
                                _currentChat.value = conversationData
                                return
                            }
                        }

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