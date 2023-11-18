package com.example.modern_practices.container

import android.app.Application
import com.example.modern_practices.di.demoModule
import com.example.modern_practices.di.interfaceModule
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(demoModule, interfaceModule)
        }
    }
}
