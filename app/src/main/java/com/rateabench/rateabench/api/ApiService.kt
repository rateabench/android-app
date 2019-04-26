package com.rateabench.rateabench.api

import com.rateabench.rateabench.models.Bench
import com.rateabench.rateabench.models.BenchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("benches")
    fun getBenchesAsync(): Deferred<Response<BenchResponse>>

    @GET("benches/{id}")
    fun getBenchAsync(@Path("id") id: Int): Deferred<Response<Bench>>
}