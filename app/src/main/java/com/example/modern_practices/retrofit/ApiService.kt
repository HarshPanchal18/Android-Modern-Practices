package com.example.modern_practices.retrofit

import retrofit2.http.GET

interface ApiService {

    companion object {
        const val BaseUrl = "https://jsonplaceholder.typicode.com/"
    }

        @GET("/post")
        suspend fun getPost(): List<Post>
}
