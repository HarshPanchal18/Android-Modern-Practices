package com.example.modern_practices.Pagination.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.modern_practices.Pagination.viewmodel.MainViewModel

@Composable
fun UserList(viewModel: MainViewModel) {

    val userList = viewModel.usersPager.collectAsLazyPagingItems()

    LazyColumn {
        items(userList) { item ->
            item?.let { UserCard(user = it) }
        }

        when (userList.loadState.append) {

            is LoadState.NotLoading -> {}

            is LoadState.Error -> {
                item { ErrorItem(message = "Some error occurred :(") }
            }

            is LoadState.Loading -> {
                item { LoadingItem() }
            }
        }

        when (userList.loadState.refresh) {

            is LoadState.NotLoading -> {}

            is LoadState.Error -> {}

            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                }
            }
        }
    }

}
