package com.rateabench.rateabench.api

import androidx.lifecycle.MutableLiveData
import com.rateabench.rateabench.models.Bench

class BenchRepository(private val api: ApiService) : BaseRepository() {

    suspend fun getBenches(benchesLiveData: MutableLiveData<Result<List<Bench>>>) {

        val benchesResult =
            safeApiCall(call = { api.getBenchesAsync().await() }, errorMessage = "Error fetching benches")
        benchesLiveData.postValue(benchesResult)
    }

    suspend fun getBench(id: Int) {
        safeApiCall(call = { api.getBenchAsync(id).await() }, errorMessage = "Error fetching bench/$$id")
    }
}