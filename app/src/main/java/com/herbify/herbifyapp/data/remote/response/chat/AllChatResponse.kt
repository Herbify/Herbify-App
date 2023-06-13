package com.herbify.herbifyapp.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class AllChatResponse(

	@field:SerializedName("data")
	val data: List<ConversationData>? = null,

	@field:SerializedName("message")
	val message: String
)

data class UserSimple(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class MessagesItem(

	@field:SerializedName("messageGroupId")
	val messageGroupId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("sender")
	val sender: Int? = null,

	@field:SerializedName("fromUser")
	val fromUser: Boolean? = null,

	@field:SerializedName("recipient")
	val recipient: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DoctorSimple(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ConversationData(

	@field:SerializedName("doctor")
	val doctorSimple: DoctorSimple? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("messages")
	val messages: List<MessagesItem?>? = null,

	@field:SerializedName("finish")
	val finish: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val userSimple: UserSimple? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
