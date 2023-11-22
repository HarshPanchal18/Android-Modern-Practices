package com.example.modern_practices.container

import com.example.modern_practices.demo.Car
import com.example.modern_practices.demo.interfaces.Main
import com.example.modern_practices.demo.viewmodel.MainViewModel
import com.example.modern_practices.qualifier.Users
import com.example.modern_practices.retrofit.RetrofitViewModel
import com.example.modern_practices.room.UserViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Component : KoinComponent {

    val car: Car by inject()

    val main:Main by inject()

    val mainViewModel:MainViewModel by inject()

    val retrofitViewModel: RetrofitViewModel by inject()

    val roomViewModel: UserViewModel by inject()

    val users:Users by inject()
}
