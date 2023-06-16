package com.herbify.herbifyapp.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class ChatbotResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("answer")
	val answer: String? = null,

	@field:SerializedName("similarQuestion")
	val similarQuestion: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
