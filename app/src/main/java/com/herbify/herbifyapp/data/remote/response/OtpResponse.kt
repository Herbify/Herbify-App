package com.herbify.herbifyapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class OtpResponse(

	@field:SerializedName("data")
	val data: OTP? = null,

	@field:SerializedName("message")
	val message: String
)

data class OTP(

	@field:SerializedName("expiredAt")
	val expiredAt: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class GenerateOtpResponse(

	@field:SerializedName("data")
	val data: GenerateOtpData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class GenerateOtpData(
	@field:SerializedName("data")
	val data: OTP? = null,

	@field:SerializedName("info")
	val info: String? = null,
)
