package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
import com.herbify.herbifyapp.data.remote.response.article.ArticleData
import com.herbify.herbifyapp.data.remote.response.article.ArticleResponse
import com.herbify.herbifyapp.data.remote.response.article.DetailArticleResponse
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.utils.RepositoryResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ArticleRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences) {

    fun addNewArticle(
        title: String,
        photo: File,
        content: String,
        tags: ArrayList<String>
    ): LiveData<RepositoryResult<AddNewArticleResponse>> {
        val result = MediatorLiveData<RepositoryResult<AddNewArticleResponse>>()
        result.value = RepositoryResult.Loading

        val userId = userPreferences.getUser().id

        val titleRequestBody = title.toRequestBody("text/plain".toMediaType())
        val photoRequestBody = photo.asRequestBody("image/*".toMediaTypeOrNull())
        val photoPart = MultipartBody.Part.createFormData("photo", photo.name, photoRequestBody)
        val contentRequestBody = content.toRequestBody("text/plain".toMediaType())
        val tagJson = JsonObject()
        for(i in 1..tags.size){
            tagJson.addProperty("tag$i", tags[i-1])
        }
        val tagRequestBody = tagJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

        val client = apiService.addNewArticle(
            userId,
            titleRequestBody,
            photoPart,
            contentRequestBody,
            tagRequestBody,
          )

        client.enqueue(object : Callback<AddNewArticleResponse> {
            override fun onResponse(
                call: Call<AddNewArticleResponse>,
                response: Response<AddNewArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        result.value = RepositoryResult.Success(responseBody)
                    } else {
                        result.value = RepositoryResult.Error("Empty response body")
                    }
                } else {
                    result.value = RepositoryResult.Error("Failed to add new article")
                }
            }

            override fun onFailure(call: Call<AddNewArticleResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }
        })

        return result
    }

    fun getAllArticle(): LiveData<RepositoryResult<List<ArticleData>>>{
        val result = MediatorLiveData<RepositoryResult<List<ArticleData>>>()
        result.value = RepositoryResult.Loading

        val client = apiService.getAllArticle()
        client.enqueue(object : Callback<ArticleResponse>{
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()!!
                    if(responseBody.data != null){
                        result.value = RepositoryResult.Success(responseBody.data)
                    }else{
                        result.value = RepositoryResult.Error(responseBody.message!!)
                    }
                }else{
                    result.value = RepositoryResult.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }

        })
        return result
    }

    fun getArticleById(id:Int): LiveData<RepositoryResult<DetailArticleResponse>>{
        val result = MediatorLiveData<RepositoryResult<DetailArticleResponse>>()
        result.value = RepositoryResult.Loading

        val client = apiService.getArticleById(id)
        client.enqueue(object : Callback<DetailArticleResponse>{
            override fun onResponse(
                call: Call<DetailArticleResponse>,
                response: Response<DetailArticleResponse>
            ) {
                if(response.isSuccessful){
                    result.value = RepositoryResult.Success(response.body()!!)
                }else{
                    result.value = RepositoryResult.Error(response.message())
                }
            }

            override fun onFailure(call: Call<DetailArticleResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }
        })
        return result
    }
}
