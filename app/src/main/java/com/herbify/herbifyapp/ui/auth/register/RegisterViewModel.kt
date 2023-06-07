package com.herbify.herbifyapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.request.RegisterRequest
import com.herbify.herbifyapp.data.remote.response.auth.UserPostResponse
import com.herbify.herbifyapp.model.UserPreferences
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreferences):ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading
    fun register(request: RegisterRequest, onRegisterSuccess : (Long, String) -> Unit){
        _isLoading.value = true
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
                    if(responseBody.data != null) {
                        onRegisterSuccess(responseBody.data.id, responseBody.data.email)
                        _isLoading.value = false
                    }
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }
}