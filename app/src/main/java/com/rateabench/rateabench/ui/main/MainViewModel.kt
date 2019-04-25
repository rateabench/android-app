package com.rateabench.rateabench.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rateabench.rateabench.ApiFactory
import com.rateabench.rateabench.api.BenchRepository
import com.rateabench.rateabench.models.Bench
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository: BenchRepository = BenchRepository(ApiFactory.benchService)


    val benchesLiveData = MutableLiveData<List<Bench>>()
    val benchLiveData = MutableLiveData<Bench>()

    fun fetchBenches() {
        scope.launch {
            val benches = repository.getBenches()
            benchesLiveData.postValue(benches)
        }
    }

    fun fetchBench(id: Int) {
        scope.launch {
            val bench = repository.getBench(id)
            benchLiveData.postValue(bench)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}
