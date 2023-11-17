package com.example.modern_practices.di

import com.example.modern_practices.demo.Car
import org.koin.dsl.module

val demoModule = module {
    factory { Car() }
    single { Car() }
}
