package com.herbify.herbifyapp.data.remote

import com.herbify.herbifyapp.data.remote.response.GenerateOtpResponse
import com.herbify.herbifyapp.data.remote.response.LoginResponse
import com.herbify.herbifyapp.data.remote.response.OtpResponse
import com.herbify.herbifyapp.data.remote.response.UserPostResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ):Call<UserPostResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<LoginResponse>

    @GET("auth/otp/{id}")
    fun getOtp(@Path("id") id: Long):Call<OtpResponse>

    @POST("auth/otp/{id}")
    fun generateOtp(@Path("id") id: Long):Call<GenerateOtpResponse>

    @FormUrlEncoded
    @POST("auth/otp/verify")
    fun verifyOtp(
        @Field("email") email: String,
        @Field("code") code: Int,
    ):Call<UserPostResponse>
}