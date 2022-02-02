package com.example.backbasecityfinder.common

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null,
    val error: Exception? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error(val exception: Exception) : Resource<Nothing>()
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
