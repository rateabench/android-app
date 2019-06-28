package com.rateabench.rateabench.api

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val customMessage: String) : Result<Nothing>()
}