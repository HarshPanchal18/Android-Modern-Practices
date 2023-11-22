package com.example.modern_practices.container

import android.app.Application
import com.example.modern_practices.demo.viewmodel.viewModelModule
import com.example.modern_practices.di.demoModule
import com.example.modern_practices.di.interfaceModule
import com.example.modern_practices.di.retrofitBuilderModule
import com.example.modern_practices.di.roomModule
import com.example.modern_practices.qualifier.usersModule
import com.example.modern_practices.scopes.scopeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                demoModule, interfaceModule, viewModelModule, retrofitBuilderModule, roomModule,
                usersModule, scopeModule
            )
            androidContext(this@BaseApplication)
        }
    }
}
