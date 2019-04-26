package com.rateabench.rateabench.api

import com.rateabench.rateabench.models.Bench

class BenchRepository(private val api: ApiService) : BaseRepository() {

    suspend fun getBenches(): List<Bench>? {

        val benches = safeApiCall(
            call = { api.getBenchesAsync().await() }, errorMessage = "Error fetching benches"
        )
        return benches?.result
    }

    suspend fun getBench(id: Int): Bench? {

        val bench = safeApiCall(
            call = { api.getBenchAsync(id).await() }, errorMessage = "Error fetching bench/$$id"
        )
        return bench
    }
}