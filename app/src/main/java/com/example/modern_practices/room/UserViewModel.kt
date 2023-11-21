package com.example.modern_practices.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel constructor(private val dao: UserDao): ViewModel() {
    fun insert(user:User) = viewModelScope.launch {
        dao.insert(user)
    }

    fun getAllUser() {
        dao.getAllUser()
    }
}
