package com.herbify.herbifyapp.ui.auth.register

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.request.RegisterRequest
import com.herbify.herbifyapp.data.remote.response.auth.UserPostResponse
import com.herbify.herbifyapp.model.UserPreferences
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreferences):ViewModel() {
    fun register(request: RegisterRequest, onRegisterSuccess : () -> Unit, onRegisterFailed: (String) -> Unit){
        val apiService = ApiConfig().getApiService()
        val params = JsonObject().apply {
            addProperty("name", request.name)
            addProperty("email", request.email)
            addProperty("password", request.password)
        }
        val client = apiService.register(params)
        client.enqueue(object : retrofit2.Callback<UserPostResponse>{
            override fun onResponse(
                call: Call<UserPostResponse>,
                response: Response<UserPostResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!
                    if(responseBody.data != null){
                        onRegisterSuccess()
                    }else{
                        onRegisterFailed(responseBody.message)
                    }
                }else{
                    onRegisterFailed(response.message())
                }
            }

            override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {
                onRegisterFailed(t.message.toString())
            }

        })
    }
}