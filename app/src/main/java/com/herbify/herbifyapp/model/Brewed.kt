package com.herbify.herbifyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("brewed")
data class Brewed(
    @PrimaryKey
    @ColumnInfo(name = "herbal_id")
    val herbalId: Int,
    @ColumnInfo(name = "herbal_image")
    val image: String,
)