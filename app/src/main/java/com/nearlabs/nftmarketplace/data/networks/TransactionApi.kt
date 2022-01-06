package com.nearlabs.nftmarketplace.data.networks

import com.nearlabs.nftmarketplace.data.networks.request.DtoSendTransactionRequest
import com.nearlabs.nftmarketplace.data.networks.response.DtoTransactionResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApi {

    @GET("transactions/list/{user_id}")
    suspend fun getTransaction(): List<DtoTransactionResponse>

    @POST("transactions/list/{user_id}")
    suspend fun sendTransaction(@Body request: DtoSendTransactionRequest): Response<ResponseBody>
}