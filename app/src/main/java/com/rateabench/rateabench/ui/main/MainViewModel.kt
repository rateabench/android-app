package com.rateabench.rateabench.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rateabench.rateabench.api.ApiFactory
import com.rateabench.rateabench.api.BenchRepository
import com.rateabench.rateabench.models.Bench
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val repository: BenchRepository = BenchRepository(ApiFactory.benchService)
    val benchesInSight = MutableLiveData<List<Bench>>()
    val benchesLiveData = MutableLiveData<List<Bench>>()

    init {
        fetchBenches()
    }

    fun fetchBenches() {
        Timber.d("Fetching benches")
        viewModelScope.launch {
            val benches = repository.getBenches()
            benchesLiveData.postValue(benches)
        }
    }
}
