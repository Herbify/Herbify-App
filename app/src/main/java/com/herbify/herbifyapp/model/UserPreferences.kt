package com.herbify.herbifyapp.model

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.dataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.concurrent.Flow

internal class UserPreferences (context: Context){
    companion object {
        private const val PREFS_KEY = "prefs_key"
        private const val NAME_KEY = "name"
        private const val ID_KEY = "id"
        private const val TOKEN_KEY = "token"

        private val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
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
            preferences.getInt(ID_KEY, 0),
            preferences.getString(PREFS_KEY, ""),
            preferences.getString(TOKEN_KEY, "")
        )
    }

    fun login(name: String, id: Int, token: String){
        preferences.edit()
            .putInt(ID_KEY, id)
            .putString(NAME_KEY, name)
            .putString(TOKEN_KEY, token)
            .apply()
    }
}