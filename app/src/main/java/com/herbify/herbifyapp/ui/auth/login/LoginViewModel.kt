package com.herbify.herbifyapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.response.auth.LoginResponse
import com.herbify.herbifyapp.model.UserModel
import com.herbify.herbifyapp.model.UserPreferences
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val pref: UserPreferences): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<UserModel>()
    val user : LiveData<UserModel> = _user

    fun login(email: String, password: String, onFailedEvent : (String) -> Unit){
        _isLoading.value = true
        val apiService = ApiConfig().getApiService()
        val params = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        }
        val client = apiService.login(params)
        client.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!
                    if(responseBody.data != null){
                        pref.login(responseBody.data.name, responseBody.data.email, responseBody.data.id, responseBody.accessToken!!, responseBody.data.status == 1)
                        _user.value = pref.getUser()
                        _isLoading.value = false
                    }else{
                        onFailedEvent(responseBody.message!!)
                    }
                }else{
                    onFailedEvent(response.message())
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onFailedEvent(t.message!!)
                _isLoading.value = false
            }
        })
    }
}