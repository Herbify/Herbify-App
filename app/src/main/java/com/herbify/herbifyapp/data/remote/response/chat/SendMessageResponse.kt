package com.herbify.herbifyapp.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class SendMessageResponse(

	@field:SerializedName("data")
	val data: MessagesItem? = null,

	@field:SerializedName("message")
	val message: String
)