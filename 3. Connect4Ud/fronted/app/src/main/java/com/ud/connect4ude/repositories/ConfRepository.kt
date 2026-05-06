package com.ud.connect4ude.repositories

import android.util.Log
import com.ud.connect4ude.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.ud.connect4ude.models.ConfigResponse
import com.ud.connect4ude.models.GameConfig


class ConfRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3001/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    fun getConfig(game: String, onResult: (GameConfig?) -> Unit) {

        Log.d("serviceapp", "fectch config $game")

        val call = api.getConfig(game)

        call.enqueue(object : Callback<ConfigResponse> {
            override fun onResponse(
                call: Call<ConfigResponse>,
                response: Response<ConfigResponse>
            ) {
                Log.d("serviceapp", response.body()?.data.toString())
                Log.d("serviceapp", response.raw().toString())

                if (response.isSuccessful) {
                    onResult(response.body()?.data)
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<ConfigResponse>, t: Throwable) {
                Log.d("serviceapp", t.message.toString())

                onResult(null)
            }
        })
    }
}