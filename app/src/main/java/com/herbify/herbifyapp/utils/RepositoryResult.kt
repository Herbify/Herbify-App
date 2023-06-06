package com.herbify.herbifyapp.utils

sealed class RepositoryResult<out R> private constructor() {
    data class Success<out T>(val data: T) : RepositoryResult<T>()
    data class Error(val error: String) : RepositoryResult<Nothing>()
    object Loading : RepositoryResult<Nothing>()
}