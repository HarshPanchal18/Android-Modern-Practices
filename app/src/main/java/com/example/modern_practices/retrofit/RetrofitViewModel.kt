package com.example.modern_practices.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RetrofitViewModel constructor(private val apiService: ApiService): ViewModel() {

    fun getPost() = viewModelScope.launch {
        apiService.getPost()
    }
}
