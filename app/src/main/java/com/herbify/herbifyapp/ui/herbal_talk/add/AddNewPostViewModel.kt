package com.herbify.herbifyapp.ui.herbal_talk.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
import com.herbify.herbifyapp.repository.ArticleRepository
import com.herbify.herbifyapp.utils.RepositoryResult
import java.io.File

class AddNewArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {
    fun addNewArticle(
        title: String,
        photo: File,
        content: String,
        tags: ArrayList<String>
    ): LiveData<RepositoryResult<AddNewArticleResponse>> {
        return articleRepository.addNewArticle(title, photo, content, tags)
    }
}
