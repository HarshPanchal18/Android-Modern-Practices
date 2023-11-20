package com.example.modern_practices.di

import com.example.modern_practices.retrofit.RetrofitViewModel
import com.example.modern_practices.retrofit.di.provideApiService
import com.example.modern_practices.retrofit.di.provideMoshi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitBuilderModule = module {
    single { provideMoshi() }
    single { provideApiService(get()) }

    viewModel { RetrofitViewModel(get()) }
}
