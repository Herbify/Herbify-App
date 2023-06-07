package com.herbify.herbifyapp.data.remote.response.article

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ArticleResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class TagArticle(

	@field:SerializedName("tag1")
	val tag1: String? = null,

	@field:SerializedName("tag2")
	val tag2: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("numLike")
	val numLike: Int? = null,

	@field:SerializedName("article")
	val article: Article? = null
) : Parcelable

@Parcelize
data class Article(

	@field:SerializedName("idUser")
	val idUser: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tag")
	val tag: Tag? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable
