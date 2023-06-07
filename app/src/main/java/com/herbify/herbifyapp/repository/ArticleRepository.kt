package com.herbify.herbifyapp.repository

import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.ApiService
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
import retrofit2.Call

class ArticleRepository(private val apiService: ApiService) {

    fun addNewArticle(article: JsonObject): Call<AddNewArticleResponse> {
        return apiService.addNewArticle(article)
    }

}