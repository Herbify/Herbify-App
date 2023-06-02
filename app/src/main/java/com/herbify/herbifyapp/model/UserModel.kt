package com.herbify.herbifyapp.model

data class UserModel(
    val id: Int,
    val name: String?,
    val token: String?,
    val email: String?,
    val isVerified: Boolean,
)
