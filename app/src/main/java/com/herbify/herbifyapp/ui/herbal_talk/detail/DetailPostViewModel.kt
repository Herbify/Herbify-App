package com.herbify.herbifyapp.ui.herbal_talk.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.data.remote.ApiConfig
import com.herbify.herbifyapp.data.remote.response.auth.UserData
import com.herbify.herbifyapp.data.remote.response.auth.UserPostResponse
import com.herbify.herbifyapp.repository.ArticleRepository
import com.herbify.herbifyapp.utils.RepositoryResult
import retrofit2.Call
import retrofit2.Response

class DetailPostViewModel(private val repository: ArticleRepository): ViewModel(){
    fun getArticle(id: Int) = repository.getArticleById(id)

    fun likeArticle(articleId: Int) = repository.likeArtice(articleId)

    fun getUserData(id: Int): LiveData<RepositoryResult<UserData>>{
        val result = MediatorLiveData<RepositoryResult<UserData>>()
        val client = ApiConfig().getApiService().getUser(id)
        client.enqueue(object : retrofit2.Callback<UserPostResponse>{
            override fun onResponse(
                call: Call<UserPostResponse>,
                response: Response<UserPostResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    if(body.data != null){
                        result.value = RepositoryResult.Success(body.data)
                    }else{
                        result.value = RepositoryResult.Error(body.message)
                    }
                }else{
                    result.value = RepositoryResult.Error(response.message())
                }
            }

            override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {
                result.value = RepositoryResult.Error(t.message.toString())
            }

        })
        return result
    }
}