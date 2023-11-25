package com.example.modern_practices

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Component : KoinComponent {
    val viewModel: MainViewModel by inject()
}
