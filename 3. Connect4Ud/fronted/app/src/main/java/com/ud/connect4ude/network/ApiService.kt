package com.ud.connect4ude.network

import com.ud.connect4ude.models.ConfigResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/game/{game}/config")
    fun getConfig(@Path("game") game: String): Call<ConfigResponse>
}