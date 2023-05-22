package com.herbify.herbifyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Herbal(
    @PrimaryKey
    val id: Int?
)
