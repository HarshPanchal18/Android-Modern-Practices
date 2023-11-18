package com.example.modern_practices.container

import com.example.modern_practices.demo.Car
import com.example.modern_practices.demo.interfaces.Main
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Component : KoinComponent {

    val car: Car by inject()

    val main:Main by inject()
}
