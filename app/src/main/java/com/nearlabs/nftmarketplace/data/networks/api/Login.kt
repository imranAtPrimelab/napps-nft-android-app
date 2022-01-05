package com.nearlabs.nftmarketplace.data.networks.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface Login {

    @POST("login")
    suspend fun login(@Body Body : JsonObject): Call<JsonObject>

    @POST("login/verify")
    suspend fun verifyUser(@Body Body : JsonObject): Call<JsonObject>

    companion object {

        private const val BASE_URL = "https://api.nearlogin.io/"

        fun create() : Login {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(Login::class.java)

        }
    }

}