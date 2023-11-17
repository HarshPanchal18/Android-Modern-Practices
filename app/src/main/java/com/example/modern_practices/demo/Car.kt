package com.example.modern_practices.demo

import android.util.Log

class Car constructor(private val engine: Engine, private val wheel: Wheel) {
    fun getCar() {
        engine.getEngine()
        wheel.getWheel()
        Log.d("Car", "getCar: Car is running")
    }

}

class Engine {
    fun getEngine(){
        Log.d("Engine", "getEngine: Engine started")
    }
}

class Wheel {
    fun getWheel(){
        Log.d("Wheel", "getWheel: Wheel started")
    }
}
