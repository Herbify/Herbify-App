package com.herbify.herbifyapp.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class AddConversationResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("finish")
	val finish: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
