package com.nearlabs.nftmarketplace.data.networks

import com.nearlabs.nftmarketplace.data.networks.response.DtoTransactionResponse
import retrofit2.http.GET

interface TransactionApi {

    @GET("transactions/list/{user_id}")
    suspend fun getTransaction(): List<DtoTransactionResponse>
}