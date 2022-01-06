package com.nearlabs.nftmarketplace.data.networks

import com.nearlabs.nftmarketplace.data.networks.response.DtoBaseResponse
import com.nearlabs.nftmarketplace.data.networks.response.DtoNFTResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NFTApi {

    @GET("nfts")
    suspend fun getAllNFTCollections(@Query("owner_id") userId : String) : DtoBaseResponse<List<DtoNFTResponse>>
}