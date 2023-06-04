package com.herbify.herbifyapp.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class UserPostResponse(

    @field:SerializedName("data")
	val data: UserData? = null,

    @field:SerializedName("message")
	val message: String
)


