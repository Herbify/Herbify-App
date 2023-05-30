package com.herbify.herbifyapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserPostResponse(

	@field:SerializedName("data")
	val data: UserData? = null,

	@field:SerializedName("message")
	val message: String
)


