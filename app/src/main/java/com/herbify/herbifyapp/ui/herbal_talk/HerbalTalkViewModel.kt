package com.herbify.herbifyapp.ui.herbal_talk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.data.remote.response.article.ArticleData
import com.herbify.herbifyapp.repository.ArticleRepository
import com.herbify.herbifyapp.utils.RepositoryResult

class HerbalTalkViewModel(private val repository: ArticleRepository): ViewModel() {
    var result = repository.getAllArticle()
}