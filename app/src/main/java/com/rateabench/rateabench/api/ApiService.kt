package com.rateabench.rateabench.api

import com.rateabench.rateabench.models.Bench
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("benches")
    fun getBenchesAsync(): Deferred<Response<List<Bench>>>

    @GET("benches/{id}")
    fun getBenchAsync(@Path("id") id: Int): Deferred<Response<Bench>>
}