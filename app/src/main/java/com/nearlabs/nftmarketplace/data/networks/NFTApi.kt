package com.nearlabs.nftmarketplace.data.networks

import com.nearlabs.nftmarketplace.data.networks.request.ClimNFTRequest
import com.nearlabs.nftmarketplace.data.networks.response.DtoBaseResponse
import com.nearlabs.nftmarketplace.data.networks.response.DtoMessageResponse
import com.nearlabs.nftmarketplace.data.networks.response.DtoNFTResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface NFTApi {

    @GET("nfts")
    suspend fun getAllNFTCollections(@Query("user_id") userId: String): DtoBaseResponse<List<DtoNFTResponse>>

    @Multipart
    @POST("nfts")
    suspend fun createNft(
        @Part("data") nftInfo: RequestBody,
        @Part file: MultipartBody.Part? = null
    ): DtoMessageResponse

    @GET("nfts/{nft_id}")
    suspend fun getNFTDetails(@Path("nft_id") nftId: String): DtoBaseResponse<DtoNFTResponse>

    @POST("nfts/{nft_id}/claim")
    suspend fun claimNFT(@Path("nft_id") nftId: String, @Body climNFTRequest: ClimNFTRequest): DtoMessageResponse
}