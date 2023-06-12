package com.herbify.herbifyapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herbify.herbifyapp.data.Injection
import com.herbify.herbifyapp.model.UserPreferences
import com.herbify.herbifyapp.ui.account.AccountViewModel
import com.herbify.herbifyapp.ui.auth.login.LoginViewModel
import com.herbify.herbifyapp.ui.auth.register.RegisterViewModel
import com.herbify.herbifyapp.ui.auth.verification.VerifikasiViewModel
import com.herbify.herbifyapp.ui.dashboard.DashboardViewModel
import com.herbify.herbifyapp.ui.herbal_doc.DoctorViewModel
import com.herbify.herbifyapp.ui.herbal_pedia.BrewViewModel
import com.herbify.herbifyapp.ui.herbal_pedia.HerbalPediaViewModel
import com.herbify.herbifyapp.ui.herbal_talk.HerbalTalkViewModel
import com.herbify.herbifyapp.ui.herbal_talk.add.AddNewArticleViewModel
import com.herbify.herbifyapp.ui.herbal_talk.detail.DetailPostViewModel
import com.herbify.herbifyapp.ui.herbal_talk.add.AddNewArticleViewModel

class ViewModelFactory(private val context: Context?): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(context != null){
            return when{
                modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                    LoginViewModel(UserPreferences.getInstance(context)) as T
                }
                modelClass.isAssignableFrom(HerbalPediaViewModel::class.java) -> {
                    HerbalPediaViewModel(Injection.provideHerbalRepository(context)) as T
                }
                modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                    RegisterViewModel(UserPreferences.getInstance(context)) as T
                }
                modelClass.isAssignableFrom(VerifikasiViewModel::class.java) -> {
                    VerifikasiViewModel(UserPreferences.getInstance(context)) as T
                }
                modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                    DashboardViewModel(Injection.provideHerbalRepository(context)) as T
                }
                modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                    AccountViewModel(UserPreferences.getInstance(context)) as T
                }
                modelClass.isAssignableFrom(DoctorViewModel::class.java) -> {
                    DoctorViewModel(Injection.provideDoctorRepository(context)) as T
                }
                modelClass.isAssignableFrom(AddNewArticleViewModel::class.java) -> {
                    AddNewArticleViewModel(Injection.provideArticleRepository(context)) as T
                }

                modelClass.isAssignableFrom(BrewViewModel::class.java) -> {
                    BrewViewModel(Injection.provideBrewRepository(context)) as T
                }
                modelClass.isAssignableFrom(AddNewArticleViewModel::class.java) -> {
                    AddNewArticleViewModel(Injection.provideArticleRepository(context)) as T
                }
                modelClass.isAssignableFrom(HerbalTalkViewModel::class.java) -> {
                    HerbalTalkViewModel(Injection.provideArticleRepository(context)) as T
                }
                modelClass.isAssignableFrom(DetailPostViewModel::class.java) -> {
                    DetailPostViewModel(Injection.provideArticleRepository(context)) as T
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }else{
            throw IllegalArgumentException("Context is null!!")
        }
    }
}