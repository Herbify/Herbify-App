package com.herbify.herbifyapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.repository.HerbalRepository

class DashboardViewModel(private val herbalRepository: HerbalRepository) : ViewModel() {
    fun herbals(): LiveData<PagingData<HerbalData>> = herbalRepository.getHerbals().cachedIn(viewModelScope)
}