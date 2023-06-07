package com.herbify.herbifyapp.data.remote.response.article

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddNewArticleResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("idUser")
	val idUser: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tag")
	val tag: Tag? = null,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable

@Parcelize
data class Tag(

	@field:SerializedName("tag1")
	val tag1: String? = null,

	@field:SerializedName("tag2")
	val tag2: String? = null
) : Parcelable
