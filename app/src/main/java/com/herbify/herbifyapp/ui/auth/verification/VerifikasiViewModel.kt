package com.herbify.herbifyapp.ui.auth.verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.response.auth.GenerateOtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.UserPostResponse
import com.herbify.herbifyapp.model.UserPreferences
import retrofit2.Call
import retrofit2.Response

class VerifikasiViewModel(private val pref: UserPreferences):ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading : LiveData<Boolean> get() =  _isLoading

    private val _otp = MutableLiveData<Int>(0)
    val otp : LiveData<Int> get() = _otp

    fun verify(typedOtp:Int, onFailureEvent: (String) -> Unit, onSuccessEvent: ()-> Unit){
        _isLoading.value = true
        val apiService = ApiConfig().getApiService()
        val client = apiService.verifyOtp(pref.getUser().email!!, typedOtp)
        client.enqueue(object : retrofit2.Callback<UserPostResponse>{
            override fun onResponse(
                call: Call<UserPostResponse>,
                response: Response<UserPostResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!
                    if (responseBody.data != null){
                        pref.verify()
                        _isLoading.value = false
                        onSuccessEvent()
                    }else{
                        onFailureEvent(responseBody.message)
                    }
                }else{
                    onFailureEvent(response.message())
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {
                onFailureEvent(t.message.toString())
                _isLoading.value = false
            }
        })
    }

    fun resendOtp(onFailureEvent: (String) -> Unit){
        val apiService = ApiConfig().getApiService()
        val client = apiService.generateOtp(pref.getUser().id!!)
        client.enqueue(object : retrofit2.Callback<GenerateOtpResponse>{
            override fun onResponse(
                call: Call<GenerateOtpResponse>,
                response: Response<GenerateOtpResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody?.data?.data != null){
                        _otp.value = responseBody.data.data.code
                    }else{
                        onFailureEvent(responseBody?.message!!)
                    }
                }else{
                    onFailureEvent(response.message())
                }
            }

            override fun onFailure(call: Call<GenerateOtpResponse>, t: Throwable) {
                onFailureEvent(t.message.toString())
            }

        })
    }
}