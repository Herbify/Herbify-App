package com.herbify.herbifyapp.data.remote

import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.response.DoctorIdResponse
import com.herbify.herbifyapp.data.remote.response.DoctorResponse
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
import com.herbify.herbifyapp.data.remote.response.article.ArticleResponse
import com.herbify.herbifyapp.data.remote.response.article.DetailArticleResponse
import com.herbify.herbifyapp.data.remote.response.auth.GenerateOtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.LoginResponse
import com.herbify.herbifyapp.data.remote.response.auth.OtpResponse
import com.herbify.herbifyapp.data.remote.response.auth.UserPostResponse
import com.herbify.herbifyapp.data.remote.response.chat.*
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

    @GET("user/{id}")
    fun getUser(@Path("id") id: Int): Call<UserPostResponse>

    @GET("auth/otp/{id}")
    fun getOtp(@Path("id") id: Int):Call<OtpResponse>

    @POST("auth/otp/{id}")
    fun generateOtp(@Path("id") id: Int):Call<GenerateOtpResponse>

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
  @POST("article")
  fun addNewArticle(
      @Part("idUser") idUser: RequestBody,
      @Part("title") title: RequestBody,
      @Part photo: MultipartBody.Part,
      @Part("content") content: RequestBody,
      @Part("tag") tag1: RequestBody
  ): Call<AddNewArticleResponse>

  @GET("article")
  fun getAllArticle(): Call<ArticleResponse>

  @GET("article/{id}")
  fun getArticleById(@Path("id") id: Int): Call<DetailArticleResponse>

  //Chat
  @GET("message/conversation")
  fun getAllConversation(): Call<AllChatResponse>

  @GET("message/conversation/{id}")
  fun getConversation(@Path("id") userId: Int) : Call<AllChatResponse>

  @GET("message/room/{id}")
  fun getConversationRoom(@Path("id") userId: Int): Call<ConversationResponse>

  @POST("message/room")
  fun createConversationRoom(
      @Body raw : JsonObject
  ): Call<SimpleConversationResponse>

  @POST("message/send")
  fun sendMessage(
      @Body raw : JsonObject
  ): Call<SendMessageResponse>

  @PUT("message/room/{id}")
  fun finishConversation(@Path("id") roomId: Int) : Call<FinishConversationResponse>
}