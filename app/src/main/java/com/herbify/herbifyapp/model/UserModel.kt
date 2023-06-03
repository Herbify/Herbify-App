package com.herbify.herbifyapp.model

data class UserModel(
    val id: Long,
    val name: String?,
    val token: String?,
    val email: String?,
    val isVerified: Boolean,
)
