package com.herbify.herbifyapp.model

class UserModel(
    val id: Int,
    val name: String?,
    val token: String?,
    val email: String?,
    val isVerified: Boolean,
){
    fun isLogged(): Boolean {
        return token != ""
    }
}
