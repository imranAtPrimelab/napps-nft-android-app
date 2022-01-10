package com.nearlabs.nftmarketplace.data.networks

import com.nearlabs.nftmarketplace.data.networks.request.DtoUserCreateRequest
import com.nearlabs.nftmarketplace.data.networks.response.DtoUserInfoResponse
import com.nearlabs.nftmarketplace.data.networks.response.DtoUserProfileResponse
import retrofit2.http.*

interface UserApi {
    @GET("users/{user_id}")
    suspend fun getUserProfile(@Path("user_id") userId: String): DtoUserProfileResponse

    @PUT("{user_id}")
    suspend fun modifyUser(@Path("user_id") userId: String): DtoUserInfoResponse

    @DELETE("{user_id}")
    suspend fun deleteUser(@Path("user_id") userId: String): DtoUserInfoResponse

    @GET("user/{user_id}/resend_code")
    suspend fun resendCode(@Path("user_id") userId: String): DtoUserInfoResponse

    @POST("user/create")
    suspend fun createUser(@Body request: DtoUserCreateRequest): DtoUserInfoResponse

    @POST("user/suggest/?walletName=moisesmarques.near&suggestionCount=10")
    suspend fun suggestWalletName(@Body request: DtoUserCreateRequest): DtoUserInfoResponse
}