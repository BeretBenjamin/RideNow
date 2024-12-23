package com.example.ridenow.MotorcycleAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:3000/" // Replace with your Node.js server's base URL

    val api: MotorcycleApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // For JSON parsing
            .build()
            .create(MotorcycleApi::class.java)
    }
}
