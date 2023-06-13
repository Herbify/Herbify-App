package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.chat.*
import com.herbify.herbifyapp.utils.RepositoryResult
import retrofit2.Call
import retrofit2.Response

class ChatRepository(private val database: HerbalDatabase, private val apiService: ApiService) {
    fun createConversation(raw: JsonObject): LiveData<RepositoryResult<SimpleConversationResponse>>{
        val result = MediatorLiveData<RepositoryResult<SimpleConversationResponse>>()
        result.value = RepositoryResult.Loading
        val client = apiService.createConversationRoom(raw)
        client.enqueue(object : retrofit2.Callback<SimpleConversationResponse>{
            override fun onResponse(
                call: Call<SimpleConversationResponse>,
                response: Response<SimpleConversationResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    result.value = RepositoryResult.Success(body)
                }
            }

            override fun onFailure(call: Call<SimpleConversationResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }

        })
        return result
    }

    fun getConversationRoom(userId: Int): LiveData<RepositoryResult<Conversation>>{
        val result = MediatorLiveData<RepositoryResult<Conversation>>()
        val client = apiService.getConversationRoom(userId)
        client.enqueue(object : retrofit2.Callback<ConversationResponse>{
            override fun onResponse(
                call: Call<ConversationResponse>,
                response: Response<ConversationResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    if(body.data != null){
                        result.value = RepositoryResult.Success(body.data)
                    }else{
                        result.value = RepositoryResult.Error(body.message)
                    }
                }
            }

            override fun onFailure(call: Call<ConversationResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }

        })
        return result
    }
    fun sendMessage(raw: JsonObject): LiveData<RepositoryResult<MessagesItem>>{
        val client = apiService.sendMessage(raw)
        val result = MediatorLiveData<RepositoryResult<MessagesItem>>()
        client.enqueue(object : retrofit2.Callback<SendMessageResponse>{
            override fun onResponse(
                call: Call<SendMessageResponse>,
                response: Response<SendMessageResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    if(body.data != null){
                        result.value = RepositoryResult.Success(body.data)
                    }else{
                        result.value = RepositoryResult.Error(body.message)
                    }
                }else{
                    result.value = RepositoryResult.Error(response.message())
                }
            }

            override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }

        })
        return result
    }

    fun finishConversation(conversation: Conversation): LiveData<RepositoryResult<SimpleConversationResponse>> {
        val result = MediatorLiveData<RepositoryResult<SimpleConversationResponse>>()
        val client = apiService.finishConversation(conversation.id)
        client.enqueue(object : retrofit2.Callback<FinishConversationResponse>{
            override fun onResponse(
                call: Call<FinishConversationResponse>,
                response: Response<FinishConversationResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    if(body.data != null){
                        result.value = RepositoryResult.Success(body.data)
                    }else{
                        result.value = RepositoryResult.Error(body.message)
                    }
                }
            }

            override fun onFailure(call: Call<FinishConversationResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }

        })
        return result
    }

}