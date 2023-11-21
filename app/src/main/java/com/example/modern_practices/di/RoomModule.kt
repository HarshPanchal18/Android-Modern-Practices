package com.example.modern_practices.di

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.modern_practices.room.UserDatabase
import org.koin.dsl.module

fun provideDatabase(application: Application): UserDatabase =
    databaseBuilder(application, UserDatabase::class.java,"user")
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(db: UserDatabase) = db.getUserDao()

val roomModule = module {
    single { provideDatabase(get()) }
    single { provideDao(get()) }
}
