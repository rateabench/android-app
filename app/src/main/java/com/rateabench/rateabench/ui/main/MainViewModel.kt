package com.rateabench.rateabench.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rateabench.rateabench.ApiFactory
import com.rateabench.rateabench.api.BenchRepository
import com.rateabench.rateabench.models.Bench
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository: BenchRepository = BenchRepository(ApiFactory.benchService)
    val currentBench = MutableLiveData<Bench>()
    val benchesLiveData = MutableLiveData<List<Bench>>()

    fun fetchBenches() {
        viewModelScope.launch {
            val benches = repository.getBenches()
            benchesLiveData.postValue(benches)
        }
    }

}
