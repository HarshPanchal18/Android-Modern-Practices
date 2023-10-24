package com.example.modern_practices.Pagination.repository

import com.example.modern_practices.Pagination.network.UserResponse

interface UserRepository {
    suspend fun getUsers(page: Int, limit: Int): UserResponse
}
