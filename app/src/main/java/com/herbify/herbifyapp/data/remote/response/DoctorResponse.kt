package com.herbify.herbifyapp.data.remote.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DoctorResponse(

	@field:SerializedName("data")
	val data: List<DoctorData>? = null,

	@field:SerializedName("message")
	val message: String
)

data class DoctorIdResponse(
	@field:SerializedName("data")
	val data: DoctorData? = null,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class DoctorData(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("verifiedAt")
	val verifiedAt: String? = null,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
):Parcelable
