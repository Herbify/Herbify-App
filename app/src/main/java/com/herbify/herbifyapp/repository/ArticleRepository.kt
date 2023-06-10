package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
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

    suspend fun addNewArticle(
        title: String,
        photo: File,
        content: String,
        tag1: String,
        tag2: String
    ): LiveData<RepositoryResult<AddNewArticleResponse>> {
        val result = MediatorLiveData<RepositoryResult<AddNewArticleResponse>>()
        result.value = RepositoryResult.Loading

        val userId = userPreferences.getUser().id

        val titleRequestBody = title.toRequestBody("text/plain".toMediaType())
        val photoRequestBody = photo.asRequestBody("image/*".toMediaTypeOrNull())
        val photoPart = MultipartBody.Part.createFormData("photo", photo.name, photoRequestBody)
        val contentRequestBody = content.toRequestBody("text/plain".toMediaType())
        val tag1RequestBody = tag1.toRequestBody("text/plain".toMediaType())
        val tag2RequestBody = tag2.toRequestBody("text/plain".toMediaType())

        val client = apiService.addNewArticle(
            userId,
            titleRequestBody,
            photoPart,
            contentRequestBody,
            tag1RequestBody,
            tag2RequestBody,
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




}
