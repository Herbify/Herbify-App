package com.herbify.herbifyapp.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class FinishConversationResponse(

	@field:SerializedName("data")
	val data: SimpleConversationResponse? = null,

	@field:SerializedName("message")
	val message: String
)
