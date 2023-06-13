package com.herbify.herbifyapp.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class ConversationResponse(

	@field:SerializedName("data")
	val data: Conversation? = null,

	@field:SerializedName("message")
	val message: String
)

data class Conversation(

	@field:SerializedName("doctor")
	val doctorSimple: DoctorSimple? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int,

	@field:SerializedName("finish")
	val finish: Boolean? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("user")
	val userSimple: UserSimple? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
