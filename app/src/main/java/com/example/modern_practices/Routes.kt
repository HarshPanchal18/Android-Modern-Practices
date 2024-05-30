package com.example.modern_practices

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object FirstScreen : Routes() // pure data obj w/o any primitive

    @Serializable
    data class SecondScreen(val primitive: String) : Routes() // data class w/ custom primitive

}
