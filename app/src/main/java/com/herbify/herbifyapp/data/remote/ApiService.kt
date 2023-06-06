package com.herbify.herbifyapp.data.remote

import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.response.DoctorResponse
import com.herbify.herbifyapp.data.remote.response.auth.GenerateOtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.LoginResponse
import com.herbify.herbifyapp.data.remote.response.auth.OtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.UserPostResponse
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/register")
    fun register(
        @Body raw: JsonObject
    ):Call<UserPostResponse>

    @POST("auth/login")
    fun login(
        @Body raw: JsonObject
    ):Call<LoginResponse>

    @GET("auth/otp/{id}")
    fun getOtp(@Path("id") id: Long):Call<OtpResponse>

    @POST("auth/otp/{id}")
    fun generateOtp(@Path("id") id: Long):Call<GenerateOtpResponse>

    @POST("auth/otp/verify")
    fun verifyOtp(
        @Body raw: JsonObject
    ):Call<UserPostResponse>

    @GET("herbal")
    suspend fun getAllHerbals(
        @Query("limit") limit:Int,
        @Query("page") page: Int
    ): HerbalResponse

    @GET("doctor")
    fun getAllDoctors(): Call<DoctorResponse>

    @GET("doctor/{id}")
    fun getDoctor(@Path("id") id: Long): Call<DoctorResponse>
}