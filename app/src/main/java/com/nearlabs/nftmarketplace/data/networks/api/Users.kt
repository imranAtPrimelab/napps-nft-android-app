package com.nearlabs.nftmarketplace.data.networks.api

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request


interface Users {
    @GET("users/id")//
    suspend fun getUser(): JsonObject

    @PUT("users/h315j-3kn1i5-315j3")
    suspend fun modifyUser(): JSONObject

    @DELETE("users/h315j-3kn1i5-315j3")
    suspend fun deleteUser(): JSONObject

    @GET("user/:user_id/resend_code")
    suspend fun resendCode(): JSONObject

    @POST("user/create")
    suspend fun createUser(@Body Body: JsonObject): Call<JsonObject>

    companion object {

        private const val BASE_URL = "https://api.nearlogin.io/"

        fun create() : Users {

            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic PEJhc2ljIEF1dGggVXNlcm5hbWU+OjxCYXNpYyBBdXRoIFBhc3N3b3JkPg==")
                    .build()
                chain.proceed(newRequest)
            }).build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(Users::class.java)

        }
    }
}