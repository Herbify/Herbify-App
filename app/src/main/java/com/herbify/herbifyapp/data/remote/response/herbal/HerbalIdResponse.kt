package com.herbify.herbifyapp.data.remote.response.herbal

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class HerbalIdResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Benefit(

	@field:SerializedName("1")
	val jsonMember1: String? = null,

	@field:SerializedName("2")
	val jsonMember2: String? = null,

	@field:SerializedName("3")
	val jsonMember3: String? = null
)

data class Data(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("scientificName")
	val scientificName: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("benefit")
	val benefit: JsonObject? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
