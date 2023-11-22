package com.example.modern_practices.qualifier

import android.util.Log
import org.koin.core.qualifier.named
import org.koin.dsl.factory
import org.koin.dsl.module

class Users(private val fName: String, private val lName: String) {

    fun getUser() {
        Log.d("Users", "getUser: $fName $lName")
    }

}

fun provideFirstName(): String = "Harsh"
fun provideLastName(): String = "Panchal"

val usersModule = module {

    factory(named("fName")) {
        provideFirstName()
    }

    factory(named("lName")) {
        provideLastName()
    }

    factory { Users(get(named("fName")),get(named("lName"))) }

}
