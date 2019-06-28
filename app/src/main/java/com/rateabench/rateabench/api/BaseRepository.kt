package com.rateabench.rateabench.api

import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): Result<T> {

        return safeApiResult(call, errorMessage)

    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>, errorMessage: String): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return Result.Error(e, errorMessage)
        }
        return Result.Error(IOException(), errorMessage)
    }
}