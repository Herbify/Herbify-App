package com.herbify.herbifyapp.model

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class UserPreferences private constructor(context: Context){
    companion object {
        private const val PREFS_KEY = "prefs_key"
        private const val NAME_KEY = "name"
        private const val EMAIL_KEY = "email"
        private const val ID_KEY = "id"
        private const val TOKEN_KEY = "token"
        private const val VERIFIED_KEY = "verified"

        private val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        @Volatile
        private var INSTANCE : UserPreferences? = null

        fun getInstance(context: Context): UserPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = UserPreferences(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private val preferences = EncryptedSharedPreferences.create(
        PREFS_KEY,
        masterKeys,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getUser(): UserModel{
        return UserModel(
            id = preferences.getLong(ID_KEY, 0),
            name = preferences.getString(NAME_KEY, null),
            token = preferences.getString(TOKEN_KEY, null),
            email = preferences.getString(EMAIL_KEY, null),
            isVerified = preferences.getBoolean(VERIFIED_KEY, false)
        )
    }

    fun hasSession():Boolean{
        Log.d("USER PREFERENCES", "Has session : ${preferences.getString(TOKEN_KEY, null) != null}")
        return preferences.getString(TOKEN_KEY,null) != null
    }

    fun isVerified(): Boolean = preferences.getBoolean(VERIFIED_KEY, false)

    fun verify(){
        preferences.edit().putBoolean(VERIFIED_KEY, true).apply()
    }

    fun login(name: String, email: String, id: Long, token: String, isVerified: Boolean){
        preferences.edit()
            .putLong(ID_KEY, id)
            .putString(NAME_KEY, name)
            .putString(EMAIL_KEY,email)
            .putString(TOKEN_KEY, token)
            .putBoolean(VERIFIED_KEY, isVerified)
            .apply()
    }

    fun logout() {
        preferences.edit()
            .putLong(ID_KEY, 0)
            .putString(NAME_KEY, null)
            .putString(EMAIL_KEY,null)
            .putString(TOKEN_KEY, null)
            .putBoolean(VERIFIED_KEY, false)
            .apply()
    }
}