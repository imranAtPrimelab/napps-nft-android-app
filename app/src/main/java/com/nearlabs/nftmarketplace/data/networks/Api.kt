package com.nearlabs.nftmarketplace.data.networks

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.networks.response.DtoUserResponse
import retrofit2.http.GET

interface Api {
    @GET("api/users")
    suspend fun sampleGet(): JsonObject

    @GET("api/users")
    suspend fun getUsers(): List<DtoUserResponse>
}