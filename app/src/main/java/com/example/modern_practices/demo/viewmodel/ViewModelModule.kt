package com.example.modern_practices.demo.viewmodel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val viewModelModule = module {

    factory { TestImpl() } bind Test::class

    viewModel { MainViewModel(get()) }
}
