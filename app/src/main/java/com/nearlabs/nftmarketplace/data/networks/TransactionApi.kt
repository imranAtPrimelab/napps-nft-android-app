package com.nearlabs.nftmarketplace.data.networks

import com.google.gson.JsonObject
import com.nearlabs.nftmarketplace.data.networks.request.DtoAddWalletRequest
import com.nearlabs.nftmarketplace.data.networks.request.DtoChangeWalletRequest
import com.nearlabs.nftmarketplace.data.networks.response.DtoTransaction
import com.nearlabs.nftmarketplace.data.networks.response.DtoUserResponse
import com.nearlabs.nftmarketplace.data.networks.response.DtoWalletResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApi {

    @GET("transactions/list/{user_id}")
    suspend fun getTransaction(): List<DtoTransaction>
}