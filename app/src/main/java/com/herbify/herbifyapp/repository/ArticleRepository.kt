package com.herbify.herbifyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.utils.RepositoryResult
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences) {

    fun addNewArticle(
        title: String,
        photo: MultipartBody.Part,
        content: String,
        tag1: String,
        tag2: String
    ): LiveData<RepositoryResult<AddNewArticleResponse>> {
        val result = MediatorLiveData<RepositoryResult<AddNewArticleResponse>>()
        result.value = RepositoryResult.Loading

        val userId = userPreferences.getUser().id

        val client = apiService.addNewArticle(
            userId,
            title,
            photo,
            content,
            tag1,
            tag2,
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
