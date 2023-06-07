package com.herbify.herbifyapp.data.remote.response.herbal

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
data class HerbalResponse(

	@field:SerializedName("data")
	val data: List<HerbalData>,

	@field:SerializedName("limit")
	val limit: Int,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Entity(tableName = "herbals")
@Parcelize
data class HerbalData(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("scientificName")
	val scientificName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("benefit")
	val benefit: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable
