package com.example.modern_practices.container

import android.app.Application
import com.example.modern_practices.demo.viewmodel.viewModelModule
import com.example.modern_practices.di.demoModule
import com.example.modern_practices.di.interfaceModule
import com.example.modern_practices.di.retrofitBuilderModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(demoModule, interfaceModule, viewModelModule, retrofitBuilderModule)
            androidContext(this@BaseApplication)
        }
    }
}
