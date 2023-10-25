package com.example.modern_practices.Pagination.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.modern_practices.Pagination.UserDataSource
import com.example.modern_practices.Pagination.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    val usersPager = Pager(
        PagingConfig(pageSize = 10)
    ) {
        UserDataSource(repo)
    }.flow.cachedIn(viewModelScope)
}
