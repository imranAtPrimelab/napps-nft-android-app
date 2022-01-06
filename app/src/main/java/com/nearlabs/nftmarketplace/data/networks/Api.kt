package com.nearlabs.nftmarketplace.data.networks

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.networks.request.DtoAddWalletRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoChangeWalletRequest
import com.nearlabs.nftmarketplace.data.networks.response.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("api/users")
    suspend fun sampleGet(): JsonObject

    @GET("api/wallets")
    suspend fun getWallets(): DtoWalletResponse

    @POST("api/change_wallet")
    suspend fun changeWallet(@Body request: DtoChangeWalletRequest): Response<ResponseBody>

    @POST("api/add_wallet")
    suspend fun addWallet(@Body request: DtoAddWalletRequest): Response<ResponseBody>

    //https://api.nearlogin.io/users/2slqFE0gPeySpwnPKHn00

    @GET("users/{user_id}")
    suspend fun getUserProfile(@Path("user_id") userId: String): DtoUserProfileResponse

}