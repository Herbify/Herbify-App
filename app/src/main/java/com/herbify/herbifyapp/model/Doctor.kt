package com.herbify.herbifyapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "doctors")
@Parcelize
data class Doctor(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("verifiedAt")
    val verifiedAt: String? = null,

    @field:SerializedName("photo")
    val photo: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
): Parcelable