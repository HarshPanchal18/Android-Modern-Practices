package com.example.modern_practices.demo.viewmodel

import android.util.Log

interface Test {
    fun getTest()
}

class TestImpl: Test {
    override fun getTest() {
        Log.d("TestImpl", "getTest: Test is Running")
    }
}
