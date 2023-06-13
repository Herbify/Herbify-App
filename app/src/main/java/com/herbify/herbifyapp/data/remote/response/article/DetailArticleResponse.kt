package com.herbify.herbifyapp.data.remote.response.article

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DetailArticleResponse(

	@field:SerializedName("data")
	val data: Article? = null,

	@field:SerializedName("like")
	val like: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class TagDetailArticle(

	@field:SerializedName("tag1")
	val tag1: String? = null,

	@field:SerializedName("tag2")
	val tag2: String? = null
) : Parcelable
