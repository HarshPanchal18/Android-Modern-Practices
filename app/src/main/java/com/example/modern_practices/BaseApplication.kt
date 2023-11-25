package com.example.modern_practices

import android.app.Application
import com.example.modern_practices.font.fontModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(fontModule)
        }
    }
}
