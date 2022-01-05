package com.nearlabs.nftmarketplace.data.networks.api

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.networks.request.DtoAddWalletRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoChangeWalletRequest
import com.nearlabs.nftmarketplace.data.networks.response.DtoUserResponse
import com.nearlabs.nftmarketplace.data.networks.response.DtoWalletResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("api/users")
    suspend fun sampleGet(): JsonObject

    @GET("api/users")
    suspend fun getUsers(): List<DtoUserResponse>

    @GET("api/wallets")
    suspend fun getWallets(): DtoWalletResponse

    @POST("api/change_wallet")
    suspend fun changeWallet(@Body request: DtoChangeWalletRequest): Response<ResponseBody>

    @POST("api/add_wallet")
    suspend fun addWallet(@Body request: DtoAddWalletRequest): Response<ResponseBody>
}