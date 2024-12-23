package com.example.ridenow.MotorcycleAPI

import com.example.ridenow.model.Motorcycle
import retrofit2.Response
import retrofit2.http.*

interface MotorcycleApi {

    @GET("motorcycles")
    suspend fun getMotorcycles(): Response<List<Motorcycle>>

    @POST("motorcycles")
    suspend fun addMotorcycle(@Body motorcycle: Motorcycle): Response<Motorcycle>

    @DELETE("motorcycles/{id}")
    suspend fun deleteMotorcycle(@Path("id") id: String): Response<Unit>
}
