package com.herbify.herbifyapp.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class UserData(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("verifiedAt")
    val verifiedAt: Any? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)