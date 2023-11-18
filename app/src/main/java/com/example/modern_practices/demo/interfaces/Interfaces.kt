package com.example.modern_practices.demo.interfaces

import android.util.Log

interface demoOne {
    fun getDemoOne()
}

interface demoTwo {
    fun getDemoTwo()
}

class DemoImpl : demoOne, demoTwo {
    override fun getDemoOne() {
        Log.d("DemoOneImpl", "getDemoOne: This is demoOne")
    }

    override fun getDemoTwo() {
        Log.d("DemoTwoImpl", "getDemoTwo: This is demoTwo")
    }
}

class Main(private val demoOne: demoOne,private val demoTwo: demoTwo) {
    fun getDemo() {
        demoOne.getDemoOne()
        demoTwo.getDemoTwo()
    }
}
