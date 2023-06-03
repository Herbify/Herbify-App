package com.herbify.herbifyapp.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herbify.herbifyapp.model.UserPreferences

class AccountViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun logout(){
        userPreferences.logout()
    }
}