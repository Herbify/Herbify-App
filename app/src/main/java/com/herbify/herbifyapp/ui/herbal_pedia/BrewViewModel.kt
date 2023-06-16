package com.herbify.herbifyapp.ui.herbal_pedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.model.Brewed
import com.herbify.herbifyapp.repository.BrewRepository
import com.herbify.herbifyapp.repository.HerbalRepository

class BrewViewModel(private val brewRepository: BrewRepository): ViewModel() {
    val herbal = brewRepository.getAllBrewed()

    fun addBrewedHerbal(herbalData: HerbalData){
        brewRepository.addBrewed(
            Brewed(
                herbalId = herbalData.id,
                herbalData.image
            )
        )
    }

    fun reset() = brewRepository.reset()
    fun delete(herbalData: Brewed) = brewRepository.deleteBrewed(herbalData.herbalId)
}