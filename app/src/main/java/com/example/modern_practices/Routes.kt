package com.example.modern_practices

import kotlinx.serialization.Serializable

// https://proandroiddev.com/jetpack-compose-screen-navigation-with-type-safety-337ec177026e
@Serializable
sealed class Routes {

    @Serializable
    data object FirstScreen : Routes() // pure data obj w/o any primitive

    @Serializable
    data class SecondScreen(val primitive: String) : Routes() // data class w/ custom primitive

}
