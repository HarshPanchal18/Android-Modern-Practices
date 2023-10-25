package com.example.modern_practices.Pagination.repository

import com.example.modern_practices.Pagination.network.UserApi
import com.example.modern_practices.Pagination.network.UserResponse

class UserRepositoryImpl(private val api: UserApi) : UserRepository {
    override suspend fun getUsers(page: Int, limit: Int): UserResponse {
        return api.getUsers(page,limit)
    }
}
