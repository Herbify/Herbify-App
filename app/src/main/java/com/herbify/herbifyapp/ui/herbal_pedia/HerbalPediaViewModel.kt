package com.herbify.herbifyapp.ui.herbal_pedia

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.Brewed
import com.herbify.herbifyapp.repository.BrewRepository
import com.herbify.herbifyapp.repository.HerbalRepository
import com.herbify.herbifyapp.utils.RepositoryResult

class HerbalPediaViewModel(private val repository: HerbalRepository, private val brewRepository: BrewRepository): ViewModel() {
    fun herbals(): LiveData<PagingData<HerbalData>> {

        return repository.getHerbals().cachedIn(viewModelScope)
    }

    fun herbal(id: Int) = repository.getHerbals(id)

    val brewedHerbal = brewRepository.getAllBrewed()
    fun delete(herbalData: Brewed) = brewRepository.deleteBrewed(herbalData.herbalId)

    fun search(key: String): LiveData<RepositoryResult<HerbalData>>{
        return repository.getHerbal(key)
    }

    fun searchHerbal(key: String): LiveData<PagingData<HerbalData>> = repository.searchHerbal(key).cachedIn(viewModelScope)
}
