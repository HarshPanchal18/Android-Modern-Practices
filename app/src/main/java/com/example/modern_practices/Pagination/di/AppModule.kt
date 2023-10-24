package com.example.modern_practices.Pagination.di

import com.example.modern_practices.Pagination.repository.UserRepository
import com.example.modern_practices.Pagination.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideUsersApi(): UserApi = UserApi()

    @Provides
    fun provideUsersRepository(api: UserApi): UserRepository = UserRepositoryImpl(api)
}
