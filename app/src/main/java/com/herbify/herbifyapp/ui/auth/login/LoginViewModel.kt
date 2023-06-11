package com.herbify.herbifyapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.response.auth.LoginResponse
import com.herbify.herbifyapp.model.UserModel
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.utils.RepositoryResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val pref: UserPreferences): ViewModel() {
    var loginResult = MutableLiveData<RepositoryResult<UserModel>>()

    fun login(email: String, password: String){
        loginResult.value = RepositoryResult.Loading
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
                        loginResult.value = RepositoryResult.Success(pref.getUser())
                    }else{
                        loginResult.value = RepositoryResult.Error(responseBody.message!!)
                    }
                }else{
                    loginResult.value = RepositoryResult.Error(response.message())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = RepositoryResult.Error(t.message.toString())
            }
        })
    }

    fun login(email: String){
        loginResult.value = RepositoryResult.Loading
        val apiService = ApiConfig().getApiService()
        val params = JsonObject().apply {
            addProperty("email", email)
            addProperty("type", "google")
        }
        val client = apiService.login(params)
        client.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!
                    if(responseBody.data != null){
                        pref.login(responseBody.data.name, responseBody.data.email, responseBody.data.id, responseBody.accessToken!!, responseBody.data.status == 1)
                        loginResult.value = RepositoryResult.Success(pref.getUser())
                    }else{
                        loginResult.value = RepositoryResult.Error(responseBody.message!!)
                    }
                }else{
                    loginResult.value = RepositoryResult.Error(response.message())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = RepositoryResult.Error(t.message.toString())
            }
        })
    }
}