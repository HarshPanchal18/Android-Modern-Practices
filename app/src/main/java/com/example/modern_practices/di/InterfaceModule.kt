package com.example.modern_practices.di

import com.example.modern_practices.demo.interfaces.DemoImpl
import com.example.modern_practices.demo.interfaces.Main
import com.example.modern_practices.demo.interfaces.demoOne
import com.example.modern_practices.demo.interfaces.demoTwo
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val interfaceModule = module {
    // factory { DemoImpl() as demoOne } - Not recommended
    // factory<demoOne> { DemoImpl() } // Second way
    // factory { DemoImpl() } bind demoOne::class // Third way
    // factory { DemoImpl() }.bind(demoOne::class) // Fourth way

    factory { DemoImpl() } binds arrayOf(demoOne::class, demoTwo::class)
    factory { Main(get(),get()) }
}
