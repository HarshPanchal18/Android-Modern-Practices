package com.example.modern_practices.font

import android.content.Context
import com.example.modern_practices.AppPreference
import com.example.modern_practices.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val fontModule = module {

    factory { AppPreference(androidContext().getSharedPreferences("app-preferences",Context.MODE_PRIVATE)) }

    viewModel { MainViewModel(get()) }

}

/*fun providePreferences(context: Context): AppPreference {
    val preferences = context.getSharedPreferences("app-preferences", Context.MODE_PRIVATE)
    return AppPreference(preferences)
}*/
