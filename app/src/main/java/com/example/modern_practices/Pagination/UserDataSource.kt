package com.example.modern_practices.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.modern_practices.Pagination.network.User
import com.example.modern_practices.Pagination.repository.UserRepository

class UserDataSource(private val repo: UserRepository): PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        TODO("Not yet implemented")
    }
}
