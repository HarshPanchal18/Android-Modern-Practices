package com.example.modern_practices.retrofit.di

import com.example.modern_practices.retrofit.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun provideApiService(moshi: Moshi): ApiService =
    Retrofit.Builder()
        .run {
            baseUrl(ApiService.BaseUrl)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            build()
        }.create(ApiService::class.java)
