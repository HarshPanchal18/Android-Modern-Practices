package com.example.modern_practices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.modern_practices.intent.CounterIntent
import com.example.modern_practices.model.CounterState

class CounterViewModel : ViewModel() {

    private val _state = MutableLiveData(CounterState())
    val state: LiveData<CounterState> get() = _state

    fun processIntent(intent: CounterIntent) {
        when (intent) {
            CounterIntent.Increment -> {
                val newCount = _state.value?.count?.plus(1) ?: 0
                _state.value = CounterState(count = newCount)
            }
        }
    }
}
