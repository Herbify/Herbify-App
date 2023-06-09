package com.herbify.herbifyapp.data.remote

import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.response.DoctorIdResponse
import com.herbify.herbifyapp.data.remote.response.DoctorResponse
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
import com.herbify.herbifyapp.data.remote.response.auth.GenerateOtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.LoginResponse
import com.herbify.herbifyapp.data.remote.response.auth.OtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.UserPostResponse
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //authentication
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
  
  
  //herbadoc
   @GET("doctor")
    fun getAllDoctors(): Call<DoctorResponse>

    @GET("doctor/{id}")
    fun getDoctor(@Path("id") id: Int): Call<DoctorIdResponse>

  //herbaltalk
  @Multipart
  @POST("api/article")
  suspend fun addNewArticle(
      @Part("title") title: RequestBody,
      @Part photo: MultipartBody.Part,
      @Part("content") content: RequestBody,
      @Part("tag[tag1]") tag1: RequestBody,
      @Part("tag[tag2]") tag2: RequestBody
  ): Call<AddNewArticleResponse>



}