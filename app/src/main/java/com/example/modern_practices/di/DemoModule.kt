package com.example.modern_practices.di

import com.example.modern_practices.demo.Car
import com.example.modern_practices.demo.Engine
import com.example.modern_practices.demo.Wheel
import org.koin.dsl.module

val demoModule = module {
    factory { Wheel() }
    factory { Engine() }

    factory { Car(get(),get()) }

    // single { Car() } - Singleton class, Creates single instance to use
}
