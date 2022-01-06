package com.nearlabs.nftmarketplace.data.networks

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.networks.request.DtoLoginRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoUserCreateRequest
import com.nearlabs.nftmarketplace.data.networks.response.DtoLoginResponse
import com.nearlabs.nftmarketplace.data.networks.response.DtoUserInfoResponse
import retrofit2.http.*

interface LoginApi {
    @POST("login")
    suspend fun login(@Body walletName : DtoLoginRequest): DtoLoginResponse

    @POST("login/verify")
    suspend fun verifyLogin(@Body walletName : DtoLoginRequest): DtoLoginResponse
}