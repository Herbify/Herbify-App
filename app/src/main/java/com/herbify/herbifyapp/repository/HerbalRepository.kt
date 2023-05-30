package com.herbify.herbifyapp.repository

import com.herbify.herbifyapp.data.local.database.HerbalDatabase
import com.herbify.herbifyapp.data.remote.ApiService

class HerbalRepository(private val database: HerbalDatabase, private val apiService: ApiService) {
}