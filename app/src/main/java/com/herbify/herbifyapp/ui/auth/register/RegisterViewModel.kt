package com.herbify.herbifyapp.ui.auth.register

import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.request.RegisterRequest
import com.herbify.herbifyapp.data.remote.response.UserPostResponse
import com.herbify.herbifyapp.model.UserPreferences
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreferences):ViewModel() {
    private fun register(request: RegisterRequest, onRegisterSuccess : () -> Unit, onRegisterFailed: (String) -> Unit){
        val apiService = ApiConfig().getApiService()
        val client = apiService.register(request.name, request.email, request.password)
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
                TODO("Not yet implemented")
            }

        })
    }
}