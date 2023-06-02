package com.herbify.herbifyapp.data.remote

import com.herbify.herbifyapp.data.remote.response.auth.GenerateOtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.LoginResponse
import com.herbify.herbifyapp.data.remote.response.auth.OtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.UserPostResponse
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("api/herbal")
    fun getAllHerbals(
        @Query("page") page: Int,
        @Query("size") size:Int
    ): HerbalResponse
}