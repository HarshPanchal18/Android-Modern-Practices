package com.example.modern_practices.scopes

import android.util.Log
import com.example.modern_practices.MainActivity
import org.koin.core.annotation.KoinReflectAPI
import org.koin.dsl.module
import org.koin.dsl.scoped

class ComponentA {
    fun getA() {
        Log.d("A", "getA: A")
    }
}

val scopeModule = module {
    scope<MainActivity> {
        scoped { ComponentA() }
    }
}
