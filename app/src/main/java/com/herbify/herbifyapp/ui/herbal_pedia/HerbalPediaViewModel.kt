package com.herbify.herbifyapp.ui.herbal_pedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.repository.HerbalRepository

class HerbalPediaViewModel(private val repository: HerbalRepository): ViewModel() {
    fun herbals(): LiveData<PagingData<HerbalData>> = repository.getHerbals().cachedIn(viewModelScope)
}